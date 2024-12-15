package aoc2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day15Test extends DayTest {
    @Override
    protected Day dayInstance() {
        return new Day15();
    }

    @Test
    public void testPart1SimpleCase() {
        String input = """
                ########
                #..O.O.#
                ##@.O..#
                #...O..#
                #.#.O..#
                #...O..#
                #......#
                ########
                
                <^^>>>vv<v>>v<<
                """;
        long result = dayInstance().executePart1(input);
        System.out.println(result);
        assertThat(result).isEqualTo(2028);
    }

    @Test
    public void testPart2SimpleCase() {
        String input = """
                #######
                #...#.#
                #.....#
                #..OO@#
                #..O..#
                #.....#
                #######
                
                <vv<<^^<<^^
                """;
        long result = dayInstance().executePart2(input);
        System.out.println(result);
        assertThat(result).isEqualTo(618);
    }

    @Override
    protected String part1ExampleInput() {
        return """
                ##########
                #..O..O.O#
                #......O.#
                #.OO..O.O#
                #..O@..O.#
                #O#..O...#
                #O..O..O.#
                #.OO.O.OO#
                #....O...#
                ##########
                
                <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
                vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
                ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
                <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
                ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
                ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
                >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
                <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
                ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
                v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
                """;
    }

    @Override
    protected long part1ExampleResult() {
        return 10092;
    }

    @Override
    protected long part1Result() {
        return 1398947L;
    }

    @Override
    protected long part2ExampleResult() {
        return 9021;
    }

    @Override
    protected long part2Result() {
        return 1397393L;
    }

}
