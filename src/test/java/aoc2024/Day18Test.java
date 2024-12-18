package aoc2024;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Day18Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day18();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 22;
    }

    @Override
    protected long part1Result() {
        return 324;
    }

    @Override
    protected long part2ExampleResult() {
        return -1;
    }

    @Override
    protected long part2Result() {
        return -1;
    }


    @Test
    void testPart1Example() {
        Map<String, String> context = Map.of(
                "width", "7",
                "height", "7",
                "fallenBytes", "12"
        );
        long result = new Day18().executePart1(part1ExampleInput(), context);
        System.out.println(result);
        assertThat(result).isEqualTo(part1ExampleResult());
    }

    @Test
    void testPart1() {
        Map<String, String> context = Map.of(
                "width", "71",
                "height", "71",
                "fallenBytes", "1024"
        );
        long result = new Day18().executePart1(input(), context);
        System.out.println(result);
        assertThat(result).isEqualTo(part1Result());
    }

    @Test
    void testPart2Example() {
        Map<String, String> context = Map.of(
                "width", "7",
                "height", "7",
                "fallenBytes", "12"
        );
        String result = new Day18().executePart2(part2ExampleInput(), context);
        System.out.println(result);
        assertThat(result).isEqualTo("6,1");
    }

    @Test
    void testPart2() {
        Map<String, String> context = Map.of(
                "width", "71",
                "height", "71",
                "fallenBytes", "1024"
        );
        String result = new Day18().executePart2(input(), context);
        System.out.println(result);
        assertThat(result).isEqualTo("46,23");
    }

}
