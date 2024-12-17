package aoc2024;

import aoc2024.utils.InputProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
                Register A: 2024
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


    @Disabled
    @Test
    void testPart2Example() {
        int result = new Day17().executePart2(part2ExampleInput());
        System.out.println(result);
        assertThat(result).isEqualTo(117440);
    }

    @Test
    void testPart2() {
        int result = new Day17().executePart2(input());
        System.out.println(result);
        assertThat(result).isEqualTo(117440);
    }
}
