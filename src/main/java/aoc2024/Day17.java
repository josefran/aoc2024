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

    public long executePart2(String input) {
        Computer computer = createComputer(input);
        Program program = createProgram(input);
        List<Integer> expectedOutput = expectedOutput(program);

        long aValue = computer.state().a() + 1;
        boolean found = false;
        while (!found) {
            if (aValue % 10000000 == 0) System.out.println("Trying with a = " + aValue);
            computer.reset(aValue, 0, 0);
            program.reset();
            List<Integer> output = computer.executeProgram(program, expectedOutput);
            if (areEquals(expectedOutput, output)) {
                found = true;
            } else {
                aValue++;
            }
//            System.out.println("A: " + aValue);
//            System.out.println("State: " + computer.state());
//            System.out.println("Outputs: " + output);
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

    private static List<Integer> expectedOutput(Program program) {
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
        for (int i = 0; i < split.length; i += 2) {
            int opcode = Integer.parseInt(split[i]);
            int operand = Integer.parseInt(split[i + 1]);
            instructions.add(new Instruction(opcode, operand));
        }
        return instructions;
    }

    private Computer createComputer(String input) {
        List<String> list = input.lines().toList();
        Pattern pattern = Pattern.compile("Register ([A-C]): (\\d+)");
        Map<Character, Long> registers = new java.util.HashMap<>(Map.of('A', 0L, 'B', 0L, 'C', 0L));
        for (int i = 0; i < 3; i++) {
            String line = list.get(i);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException("Invalid input");
            String letter = matcher.group(1);
            long value = Long.parseLong(matcher.group(2));
            registers.put(letter.charAt(0), value);
        }
        Registers state = new Registers(registers.get('A'), registers.get('B'), registers.get('C'));
        return new Computer(state);
    }

    static class Registers {
        private long a;
        private long b;
        private long c;

        Registers(long a, long b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public long a() {
            return a;
        }

        public long b() {
            return b;
        }

        public long c() {
            return c;
        }

        public void setA(long a) {
            this.a = a;
        }

        public void setB(long b) {
            this.b = b;
        }

        public void setC(long c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "Registers[" +
                    "a=" + a + ", " +
                    "b=" + b + ", " +
                    "c=" + c + ']';
        }

        public void reset(long a, long b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    static final class Computer {
        private final Registers registers;

        Computer(Registers initialRegisters) {
            this.registers = initialRegisters;
        }

        Registers state() {
            return registers;
        }

        @SuppressWarnings("SameParameterValue")
        void reset(long a, long b, long c) {
            registers.reset(a, b, c);
        }

        List<Integer> executeProgram(Program program) {
            return executeProgram(program, Collections.emptyList());
        }

        List<Integer> executeProgram(Program program, List<Integer> resultToCheck) {
            List<Integer> outputs = new ArrayList<>();
            Set<String> executedInstructions = new HashSet<>();
            int threshold = resultToCheck.isEmpty() ? Integer.MAX_VALUE : resultToCheck.size();
            while (program.hasNext() && outputs.size() <= threshold) {
                String cache = program.instructionPointer() + "-" + registers.a() + "-" + registers.b() + "-" + registers.c();
                if (!executedInstructions.add(cache)) {
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
                if (!resultToCheck.isEmpty()) {
                    boolean isSubsequence = isSubsequence(outputs, resultToCheck);
                    if (!isSubsequence) break;
                }
            }
            return outputs;
        }

        private static boolean isSubsequence(List<Integer> outputs, List<Integer> resultToCheck) {
            return resultToCheck.size() >= outputs.size()
                    && resultToCheck.subList(0, outputs.size()).equals(outputs);
        }

        private void adv(int operand) {
            long value = division(operand);
            registers.setA(value);
        }

        private void bdv(int operand) {
            long value = division(operand);
            registers.setB(value);
        }

        private void cdv(int operand) {
            long value = division(operand);
            registers.setC(value);
        }

        private long division(int operand) {
            long numerator = state().a();
            long denominator = (long) Math.pow(2, combo(operand));
            return Math.floorDiv(numerator, denominator);
        }

        private void bxl(int operand) {
            long value = state().b() ^ operand;
            registers.setB(value);
        }

        private void bst(int operand) {
            long value = combo(operand) % 8;
            long valueLowest3bits = value & 0b111;
            registers.setB(valueLowest3bits);
        }

        private void jnz(int operand, Program program) {
            if (state().a() != 0) program.jump(operand);
        }

        private void bxc() {
            long value = state().b() ^ state().c();
            registers.setB(value);
        }

        private int out(int operand) {
            return (int) combo(operand) % 8;
        }

        private long combo(int operand) {
            if (operand < 0 || operand > 6) {
                throw new IllegalArgumentException("Invalid operand in combo: " + operand);
            }
            return switch (operand) {
                case 4 -> state().a();
                case 5 -> state().b();
                case 6 -> state().c();
                default -> operand;
            };
        }
    }

    static final class Program {
        private final List<Instruction> instructions;
        private int instructionPointer = 0;

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
