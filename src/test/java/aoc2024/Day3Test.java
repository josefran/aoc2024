package aoc2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day3Test extends DayTest{

    @Override
    protected Day dayInstance() {
        return new Day3();
    }

    @Override
    protected String part1ExampleInput() {
        return "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    }

    @Override
    protected String part2ExampleInput() {
        return "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
    }

    @Override
    protected int part1ExampleResult() {
        return 161;
    }

    @Override
    protected int part1Result() {
        return 190604937;
    }

    @Override
    protected int part2ExampleResult() {
        return 48;
    }

    @Override
    protected int part2Result() {
        return 82857512;
    }

}