package avc2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day4Test {

    @Test
    void testPart1() {
        String input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;
        int result = new Day4().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(18);
    }

    @Test
    void testPart1Result() {
        String input = InputProvider.inputToString("day4");
        int result = new Day4().executePart1(input);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;
        int result = new Day4().executePart2(input);
        System.out.println(result);
        assertThat(result).isEqualTo(9);
    }

    @Test
    void testPart2Result() {
        String input = InputProvider.inputToString("day4");
        int result = new Day4().executePart2(input);
        System.out.println(result);
    }

}