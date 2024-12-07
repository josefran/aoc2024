package aoc2024;

class Day1Test extends DayTest {

    @Override
    protected Day dayInstance() {
        return new Day1();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 11;
    }

    @Override
    protected long part1Result() {
        return 2769675;
    }

    @Override
    protected long part2ExampleResult() {
        return 31;
    }

    @Override
    protected long part2Result() {
        return 24643097;
    }

}