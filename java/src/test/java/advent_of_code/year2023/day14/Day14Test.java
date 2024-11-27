package advent_of_code.year2023.day14;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day14Test {

    Day14 day = new Day14();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day14/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(136);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day14/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(108641);
    }

    @Test
    void should_cycle_example() {
        List<String> lines = getLines("year2023/day14/example.txt");

        var result = day.cycle(lines, 0);

        List<String> expected = List.of(
            ".....#....",
            "....#...O#",
            "...OO##...",
            ".OO#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#....",
            "......OOOO",
            "#...O###..",
            "#..OO#...."
        );
        for (int i = 0; i < result.result().size(); i++) {
            assertThat(result.result().get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day14/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(64);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day14/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(84328);
    }
}
