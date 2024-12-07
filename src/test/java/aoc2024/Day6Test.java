package aoc2024;

class Day6Test extends DayTest {

    @Override
    protected Day dayInstance() {
        return new Day6();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 41;
    }

    @Override
    protected long part1Result() {
        return 5131;
    }

    @Override
    protected long part2ExampleResult() {
        return 6;
    }

    @Override
    protected long part2Result() {
        return 1784;
    }
}