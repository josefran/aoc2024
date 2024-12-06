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
    protected int part1ExampleResult() {
        return 41;
    }

    @Override
    protected int part1Result() {
        return 5131;
    }

    @Override
    protected int part2ExampleResult() {
        return 6;
    }

    @Override
    protected int part2Result() {
        return 1784;
    }
}