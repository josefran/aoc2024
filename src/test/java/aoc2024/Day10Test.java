package aoc2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day10();
    }

    private static Stream<Arguments> provideTestCasesForPart1() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("""
                    0123
                    1234
                    8765
                    9876
                    """, 1),
                org.junit.jupiter.params.provider.Arguments.of("""
                    ...0...
                    ...1...
                    ...2...
                    6543456
                    7.....7
                    8.....8
                    9.....9
                    """, 2),
                org.junit.jupiter.params.provider.Arguments.of("""
                    ..90..9
                    ...1.98
                    ...2..7
                    6543456
                    765.987
                    876....
                    987....
                    """, 4),
                org.junit.jupiter.params.provider.Arguments.of("""
                    10..9..
                    2...8..
                    3...7..
                    4567654
                    ...8..3
                    ...9..2
                    .....01
                    """, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForPart1")
    void testPart1Parameterized(String input, long expected) {
        long result = dayInstance().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(expected);
    }

    @Override
    protected String part1ExampleInput() {
        return """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 36;
    }

    @Override
    protected long part1Result() {
        return 472;
    }

    @Override
    protected long part2ExampleResult() {
        return 81;
    }

    @Override
    protected long part2Result() {
        return 969;
    }
}
