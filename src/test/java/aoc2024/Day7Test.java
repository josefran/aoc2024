package aoc2024;

public class Day7Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day7();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 3749;
    }

    @Override
    protected long part1Result() {
        return 5702958180383L;
    }

    @Override
    protected long part2ExampleResult() {
        return 11387;
    }

    @Override
    protected long part2Result() {
        return 92612386119138L;
    }
}
