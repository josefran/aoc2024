package aoc2024;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day14Test extends DayTest {

    @Override
    protected Day dayInstance() {
        return new Day14();
    }

    @Override
    protected String obtainInput(String resourceName) {
        return "101,103\n" + super.obtainInput(resourceName);
    }

    @Override
    protected String part1ExampleInput() {
        String input = """
                p=0,4 v=3,-3
                p=6,3 v=-1,-3
                p=10,3 v=-1,2
                p=2,0 v=2,-1
                p=0,0 v=1,3
                p=3,0 v=-2,-2
                p=7,6 v=-1,-3
                p=3,0 v=-1,-2
                p=9,3 v=2,3
                p=7,3 v=-1,2
                p=2,4 v=2,-3
                p=9,5 v=-3,-3
                """;
        // Add new line before input
        return "11,7\n" + input;
    }

    @Disabled("Not needs to be implemented")
    @Test
    void testPart2Example() {
        super.testPart2Example();
    }

    @Override
    protected long part1ExampleResult() {
        return 12;
    }

    @Override
    protected long part1Result() {
        return 221142636;
    }

    @Override
    protected long part2ExampleResult() {
        return -1;
    }

    @Override
    protected long part2Result() {
        return 7916;
    }
}
