package aoc2024;

class Day4Test extends DayTest{

    @Override
    protected Day dayInstance() {
        return new Day4();
    }

    @Override
    protected String part1ExampleInput() {
        return """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;
    }

    @Override
    protected long part1ExampleResult() {
        return 18;
    }

    @Override
    protected long part1Result() {
        return 2447;
    }

    @Override
    protected long part2ExampleResult() {
        return 9;
    }

    @Override
    protected long part2Result() {
        return 1868;
    }

}