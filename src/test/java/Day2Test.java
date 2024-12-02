import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Day2Test {

    @Test
    void testPar1() {
        String input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
                """.trim();
        List<String> lines = List.of(input.split("\n"));
        int result = new Day2().executePart1(lines);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testPart1Result() {
        List<String> lines = InputProvider.input("day2");
        int result = new Day2().executePart1(lines);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
                """.trim();
        List<String> lines = List.of(input.split("\n"));
        int result = new Day2().executePart2(lines);
        assertThat(result).isEqualTo(4);
    }

    @Test
    void testPart2Result() {
        List<String> lines = InputProvider.input("day2");
        int result = new Day2().executePart2(lines);
        System.out.println(result);
    }

    @Test
    void testErrorCase() {
        Day2.Report report = new Day2.Report(List.of(78, 75, 77, 80, 81, 82));
        assertThat(report.isSecure()).describedAs("Report should be non secure").isFalse();
        assertThat(report.isSecureWithTolerance()).describedAs("Report should be non secure with tolerance").isFalse();
    }

}