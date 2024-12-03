package avc2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Day3Test {

    @Test
    void testPart1() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        int result = new Day3().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(161);
    }

    @Test
    void testPart1Result() {
        String input = InputProvider.inputToString("day3");
        int result = new Day3().executePart1(input);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        int result = new Day3().executePart2(input);
        System.out.println(result);
        assertThat(result).isEqualTo(48);
    }

    @Test
    void testPart2Result() {
        String input = InputProvider.inputToString("day3");
        int result = new Day3().executePart2(input);
        System.out.println(result);
    }

}