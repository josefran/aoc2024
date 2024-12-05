package avc2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Day5Test {

    @Test
    void testPart1() {
        String input = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """;
        int result = new Day5().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(143);
    }

    @Test
    void testPart1Result() {
        String input = InputProvider.inputToString("day5");
        int result = new Day5().executePart1(input);
        System.out.println(result);
    }

    @Test
    void testPart2() {
        String input = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """;
        int result = new Day5().executePart2(input);
        System.out.println(result);
        assertThat(result).isEqualTo(123);
    }

    @Test
    void testPart2Result() {
        String input = InputProvider.inputToString("day5");
        int result = new Day5().executePart2(input);
        System.out.println(result);
    }

}