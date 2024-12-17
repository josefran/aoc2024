package aoc2024;

import aoc2024.utils.InputProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class DayTest {

    protected String input() {
        String resourceName = dayInstance().getClass().getSimpleName().toLowerCase() + "/input";
        return InputProvider.inputToString(resourceName);
    }
    protected abstract Day dayInstance();
    protected abstract String part1ExampleInput();
    protected String part2ExampleInput() {
        return part1ExampleInput();
    }

    protected abstract long part1ExampleResult();

    protected abstract long part1Result();

    protected abstract long part2ExampleResult();

    protected abstract long part2Result();

    @Test
    void testPart1Example() {
        long result = dayInstance().executePart1(part1ExampleInput());
        System.out.println(result);
        assertThat(result).isEqualTo(part1ExampleResult());
    }

    @Test
    void testPart1() {
        long result = dayInstance().executePart1(input());
        System.out.println(result);
        assertThat(result).isEqualTo(part1Result());
    }

    @Test
    void testPart2Example() {
        long result = dayInstance().executePart2(part2ExampleInput());
        System.out.println(result);
        assertThat(result).isEqualTo(part2ExampleResult());
    }

    @Test
    void testPart2() {
        long result = dayInstance().executePart2(input());
        System.out.println(result);
        assertThat(result).isEqualTo(part2Result());
    }

}
