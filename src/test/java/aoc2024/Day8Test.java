package aoc2024;

public class Day8Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day8();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 14;
    }

    @Override
    protected long part1Result() {
        return 361;
    }

    @Override
    protected long part2ExampleResult() {
        return 34;
    }

    @Override
    protected long part2Result() {
        return 1249;
    }
}
