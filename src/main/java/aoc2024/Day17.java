package aoc2024;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day17 {
    public String executePart1(String input) {
        Computer computer = createComputer(input);
        Program program = createProgram(input);
        List<Integer> result = computer.executeProgram(program);
        return result.stream()
                .map(o -> Integer.toString(o))
                .collect(Collectors.joining(","));
    }

    public int executePart2(String input) {
        Computer computer = createComputer(input);
        Program program = createProgram(input);
        int aValue = computer.a.value() + 1;
        boolean equals = false;
        List<Integer> initial = initialList(program);
        int threshold = initial.size();
        while (!equals) {
            //if (aValue % 10000 == 0) System.out.println("Trying with a = " + aValue);
            List<Integer> result = computer.executeProgram(program, new Register(aValue), threshold);
            equals = areEquals(initial, result);
            if (!equals) aValue++;
        }
        return aValue;
    }

    private boolean areEquals(List<Integer> initial, List<Integer> result) {
        if (initial.size() != result.size()) return false;
        for (int i = 0; i < initial.size(); i++) {
            if (!initial.get(i).equals(result.get(i))) return false;
        }
        return true;
    }

    private static List<Integer> initialList(Program program) {
        List<Integer> initial = new ArrayList<>();
        for (Instruction instruction : program.instructions()) {
            initial.add(instruction.opcode());
            initial.add(instruction.operand());
        }
        return initial;
    }

    private Program createProgram(String input) {
        List<String> lines = input.lines().skip(4).toList();
        Pattern pattern = Pattern.compile("Program: ([0-7,]+)");
        Matcher matcher = pattern.matcher(lines.getFirst());
        if (!matcher.find()) throw new IllegalArgumentException("Invalid input");
        String instructionsString = matcher.group(1);
        List<Instruction> instructions = parseInstructions(instructionsString);
        return new Program(instructions);
    }

    private static List<Instruction> parseInstructions(String instructionsString) {
        String[] split = instructionsString.split(",");
        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < split.length; i+=2) {
            int opcode = Integer.parseInt(split[i]);
            int operand = Integer.parseInt(split[i+1]);
            instructions.add(new Instruction(opcode, operand));
        }
        return instructions;
    }

    private Computer createComputer(String input) {
        List<String> list = input.lines().toList();
        Pattern pattern = Pattern.compile("Register ([A-C]): (\\d+)");
        Map<Character, Integer> registers = new java.util.HashMap<>(Map.of('A', 0, 'B', 0, 'C', 0));
        for (int i = 0; i < 3; i++) {
            String line = list.get(i);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException("Invalid input");
            String letter = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));
            registers.put(letter.charAt(0), value);
        }
        return new Computer(
                new Register(registers.get('A')),
                new Register(registers.get('B')),
                new Register(registers.get('C')));
    }

    static final class Computer {
        private Register a;
        private Register b;
        private Register c;

        Computer(Register a, Register b, Register c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        List<Integer> executeProgram(Program program, Register a, int threshold) {
            this.a = a;
            this.b = new Register(0);
            this.c = new Register(0);
            program.reset();
            return executeProgram(program, threshold);
        }

        List<Integer> executeProgram(Program program) {
            return executeProgram(program, Integer.MAX_VALUE);
        }

        List<Integer> executeProgram(Program program, int threshold) {
            List<Integer> outputs = new ArrayList<>();
            Set<String> executedInstructions = new HashSet<>();

            while (program.hasNext() && outputs.size() <= threshold) {
                String state = program.instructionPointer() + "-" + a.value() + "-" + b.value() + "-" + c.value();
                if (!executedInstructions.add(state)) {
                    System.out.println("Loop detected at instruction pointer: " + program.instructionPointer);
                    break;
                }

                Instruction instruction = program.next();
                switch (instruction.opcode()) {
                    case 0 -> adv(instruction.operand());
                    case 1 -> bxl(instruction.operand());
                    case 2 -> bst(instruction.operand());
                    case 3 -> jnz(instruction.operand(), program);
                    case 4 -> bxc();
                    case 5 -> outputs.add(out(instruction.operand()));
                    case 6 -> bdv(instruction.operand());
                    case 7 -> cdv(instruction.operand());
                }
            }
            return outputs;
        }

        private void adv(int operand) {
            int value = division(operand);
            a = new Register(value);
        }

        private int division(int operand) {
            int numerator = a.value();
            int denominator = (int) Math.pow(2, combo(operand));
            return Math.floorDiv(numerator , denominator);
        }

        private void bxl(int operand) {
            int value = b.value() ^ operand;
            b = new Register(value);
        }

        private void bst(int operand) {
            int value = combo(operand) % 8;
            int valueLowest3bits = value & 0b111;
            b = new Register(valueLowest3bits);
        }

        private void jnz(int operand, Program program) {
            if (a.value() != 0) program.jump(operand);
        }

        private void bxc() {
            int value = b.value() ^ c.value();
            b = new Register(value);
        }

        private int out(int operand) {
            return combo(operand) % 8;
        }

        private void bdv(int operand) {
            int value = division(operand);
            b = new Register(value);
        }

        private void cdv(int operand) {
            int value = division(operand);
            c = new Register(value);
        }

        private int combo(int opcode) {
            if (opcode < 0 || opcode > 6) {
                throw new IllegalArgumentException("Invalid opcode in combo: " + opcode);
            }
            return switch (opcode) {
                case 4 -> a.value();
                case 5 -> b.value();
                case 6 -> c.value();
                default -> opcode;
            };
        }

    }

    record Register(int value) {

    }

    static final class Program {
        private final List<Instruction> instructions;
        int instructionPointer = 0;

        Program(List<Instruction> instructions) {
            this.instructions = new ArrayList<>(instructions);
        }

        public boolean hasNext() {
            return instructionPointer < instructions.size();
        }

        public Instruction next() {
            return instructions.get(instructionPointer++);
        }

        public void jump(int pointer) {
            instructionPointer = pointer;
        }

        public List<Instruction> instructions() {
            return instructions;
        }

        public int instructionPointer() {
            return instructionPointer;
        }

        public void reset() {
            instructionPointer = 0;
        }
    }

    record Instruction(int opcode, int operand) {

        Instruction {
            if (opcode < 0 || opcode > 7) {
                throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }
        }

    }
}
