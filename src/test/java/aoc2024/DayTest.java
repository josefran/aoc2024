package aoc2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class DayTest {

    private Day day;
    private String part1ExampleInput;
    private String part2ExampleInput;
    private String input;
    private int part1ExampleResult;
    private int part1Result;
    private int part2ExampleResult;
    private int part2Result;

    @BeforeEach
    void setup() {
        day = dayInstance();
        part1ExampleInput = part1ExampleInput();
        part2ExampleInput = part2ExampleInput();
        String resourceName = day.getClass().getSimpleName().toLowerCase() + "/input";
        input = InputProvider.inputToString(resourceName);
        part1ExampleResult = part1ExampleResult();
        part1Result = part1Result();
        part2ExampleResult = part2ExampleResult();
        part2Result = part2Result();
    }

    protected abstract Day dayInstance();
    protected abstract String part1ExampleInput();
    protected String part2ExampleInput() {
        return part1ExampleInput();
    }
    protected abstract int part1ExampleResult();
    protected abstract int part1Result();
    protected abstract int part2ExampleResult();
    protected abstract int part2Result();

    @Test
    void testPart1Example() {
        int result = day.executePart1(part1ExampleInput);
        System.out.println(result);
        assertThat(result).isEqualTo(part1ExampleResult);
    }

    @Test
    void testPart1() {
        int result = day.executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(part1Result);
    }

    @Test
    void testPart2Example() {
        int result = day.executePart2(part2ExampleInput);
        System.out.println(result);
        assertThat(result).isEqualTo(part2ExampleResult);
    }

    @Test
    void testPart2() {
        int result = day.executePart2(input);
        System.out.println(result);
        assertThat(result).isEqualTo(part2Result);
    }

}