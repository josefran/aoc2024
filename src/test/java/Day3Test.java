import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Day3Test {

    @Test
    void testPart1() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        List<String> lines = List.of(input.split("\n"));
        int result = new Day3().executePart1(lines);
        assertThat(result).isEqualTo(161);
    }

    @Test
    void testPart1Result() {
        List<String> lines = InputProvider.input("day2");
        int result = new Day3().executePart1(lines);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        List<String> lines = List.of(input.split("\n"));
        int result = new Day3().executePart2(lines);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testPart2Result() {
        List<String> lines = InputProvider.input("day2");
        int result = new Day3().executePart2(lines);
        System.out.println(result);
    }

}