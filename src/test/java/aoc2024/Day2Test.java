package aoc2024;

class Day2Test extends DayTest {

    @Override
    protected Day dayInstance() {
        return new Day2();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 2;
    }

    @Override
    protected long part1Result() {
        return 224;
    }

    @Override
    protected long part2ExampleResult() {
        return 4;
    }

    @Override
    protected long part2Result() {
        return 293;
    }

}