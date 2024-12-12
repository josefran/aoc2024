package aoc2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day12();
    }

    private static Stream<Arguments> provideTestCasesForPart1() {
        return Stream.of(
                Arguments.of("""
                    AAAA
                    BBCD
                    BBCC
                    EEEC
                    """, 140),
                Arguments.of("""
                    OOOOO
                    OXOXO
                    OOOOO
                    OXOXO
                    OOOOO
                    """, 772)
        );
    }

    private static Stream<Arguments> provideTestCasesForPart2() {
        return Stream.of(
                Arguments.of("""
                    AAAA
                    BBCD
                    BBCC
                    EEEC
                    """, 80),
                Arguments.of("""
                    EEEEE
                    EXXXX
                    EEEEE
                    EXXXX
                    EEEEE
                    """, 236),
                Arguments.of("""
                    AAAAAA
                    AAABBA
                    AAABBA
                    ABBAAA
                    ABBAAA
                    AAAAAA
                    """, 368)
        );
    }


    @MethodSource("provideTestCasesForPart1")
    @ParameterizedTest
    void testPart1SimpleCases(String simpleCase, long expected) {
        long result = dayInstance().executePart1(simpleCase);
        System.out.println(result);
        assertThat(result).isEqualTo(expected);
    }

    @Override
    protected String part1ExampleInput() {
        return """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 1930;
    }

    @Override
    protected long part1Result() {
        return 1400386;
    }


    @MethodSource("provideTestCasesForPart2")
    @ParameterizedTest
    void testPart2SimpleCases(String simpleCase, long expected) {
        long result = dayInstance().executePart2(simpleCase);
        System.out.println(result);
        assertThat(result).isEqualTo(expected);
    }


    @Override
    protected long part2ExampleResult() {
        return 1206;
    }

    @Override
    protected long part2Result() {
        return -1;
    }
}
