package avc2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    @Test
    void testPar1() {
        String input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """.trim();
        List<String> lines = List.of(input.split("\n"));
        int result = new Day1().executePart1(lines);
        assertEquals(11, result);
    }

    @Test
    void testPart1Result() {
        List<String> lines = InputProvider.input("day1");
        int result = new Day1().executePart1(lines);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """.trim();
        List<String> lines = List.of(input.split("\n"));
        int result = new Day1().executePart2(lines);
        assertEquals(31, result);
    }

    @Test
    void testPart2Result() {
        List<String> lines = InputProvider.input("day1");
        int result = new Day1().executePart2(lines);
        System.out.println(result);
    }
}