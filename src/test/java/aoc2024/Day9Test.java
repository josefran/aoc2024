package aoc2024;

public class Day9Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day9();
    }

    @Override
    protected String part1ExampleInput() {
        return "2333133121414131402";
    }

    @Override
    protected long part1ExampleResult() {
        return 1928;
    }

    @Override
    protected long part1Result() {
        return 6211348208140L;
    }

    @Override
    protected long part2ExampleResult() {
        return 2858;
    }

    @Override
    protected long part2Result() {
        return 6239783302560L;
    }
}
