package aoc2024;

public class Day13Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day13();
    }

    @Override
    protected String part1ExampleInput() {
        return """
                Button A: X+94, Y+34
                Button B: X+22, Y+67
                Prize: X=8400, Y=5400
                
                Button A: X+26, Y+66
                Button B: X+67, Y+21
                Prize: X=12748, Y=12176
                
                Button A: X+17, Y+86
                Button B: X+84, Y+37
                Prize: X=7870, Y=6450
                
                Button A: X+69, Y+23
                Button B: X+27, Y+71
                Prize: X=18641, Y=10279
                
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 480;
    }

    @Override
    protected long part1Result() {
        return 35997;
    }

    @Override
    protected long part2ExampleResult() {
        return 875318608908L;
    }

    @Override
    protected long part2Result() {
        return 82510994362072L;
    }
}
