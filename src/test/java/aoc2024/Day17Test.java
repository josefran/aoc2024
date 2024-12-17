package aoc2024;

import aoc2024.utils.InputProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17Test {

    protected Day17 dayInstance() {
        return new Day17();
    }

    protected String input() {
        String resourceName = dayInstance().getClass().getSimpleName().toLowerCase() + "/input";
        return InputProvider.inputToString(resourceName);
    }

    protected String part1ExampleInput() {
        return """
                Register A: 729
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0
                """;
    }

    protected String part2ExampleInput() {
        return """
                Register A: 2024
                Register B: 0
                Register C: 0
                
                Program: 0,3,5,4,3,0
                """;
    }

    @Test
    void testPart1SimpleCase() {
        String input = """
                Register A: 117440
                Register B: 0
                Register C: 0
                
                Program: 0,3,5,4,3,0
                """;
        String result = new Day17().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo("0,3,5,4,3,0");
    }

    @Test
    void testPart1Example() {
        String result = new Day17().executePart1(part1ExampleInput());
        System.out.println(result);
        assertThat(result).isEqualTo("4,6,3,5,6,3,5,2,1,0");
    }

    @Test
    void testPart1() {
        String result = new Day17().executePart1(input());
        System.out.println(result);
        assertThat(result).isEqualTo("2,7,6,5,6,0,2,3,1");
    }

    @Test
    void testPart2Example() {
        long result = new Day17().executePart2(part2ExampleInput());
        System.out.println(result);
        assertThat(result).isEqualTo(117440);
    }

    @Disabled
    @Test
    void testPart2() {
        long result = new Day17().executePart2(input());
        System.out.println(result);
        assertThat(result).isEqualTo(33940147);
    }


    @Test
    void testCases1() {
        // 1. If register C contains 9, the program 2,6 would set register B to 1.
        Day17.Registers registers = new Day17.Registers(0, 0, 9);
        Day17.Computer computer = new Day17.Computer(registers);
        Day17.Program program = new Day17.Program(List.of(new Day17.Instruction(2, 6)));

        computer.executeProgram(program);

        assertThat(computer.state().b()).isEqualTo(1);
    }

    @Test
    void testCases2() {
        // 2. If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
        Day17.Registers registers = new Day17.Registers(10, 0, 0);
        Day17.Computer computer = new Day17.Computer(registers);
        Day17.Program program = new Day17.Program(List.of(
                new Day17.Instruction(5, 0),
                new Day17.Instruction(5, 1),
                new Day17.Instruction(5, 4)
        ));

        List<Integer> integers = computer.executeProgram(program);

        assertThat(integers).containsExactly(0, 1, 2);
    }

    @Test
    void testCases3() {
        // 3. If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.
        Day17.Registers registers = new Day17.Registers(2024, 0, 0);
        Day17.Computer computer = new Day17.Computer(registers);
        Day17.Program program = new Day17.Program(List.of(
                new Day17.Instruction(0, 1),
                new Day17.Instruction(5, 4),
                new Day17.Instruction(3, 0)
        ));

        List<Integer> integers = computer.executeProgram(program);

        assertThat(integers).containsExactly(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0);
        assertThat(computer.state().a()).isEqualTo(0);
    }

    @Test
    void testCases4() {
        // 4. If register B contains 29, the program 1,7 would set register B to 26.
        Day17.Registers registers = new Day17.Registers(0, 29, 0);
        Day17.Computer computer = new Day17.Computer(registers);
        Day17.Program program = new Day17.Program(List.of(
                new Day17.Instruction(1, 7)

        ));

        computer.executeProgram(program);

        assertThat(computer.state().b()).isEqualTo(26);
    }

    @Test
    void testCases5() {
        // 5. If register B contains 2024 and register C contains 43690, the program 4,0 would set register B to 44354.
        Day17.Registers registers = new Day17.Registers(0, 2024, 43690);
        Day17.Computer computer = new Day17.Computer(registers);
        Day17.Program program = new Day17.Program(List.of(
                new Day17.Instruction(4, 0)
        ));

        computer.executeProgram(program);

        assertThat(computer.state().b()).isEqualTo(44354);
    }

}
