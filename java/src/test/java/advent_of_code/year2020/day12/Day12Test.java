package advent_of_code.year2020.day12;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day12Test {

    Day12 day = new Day12();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day12/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(25);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day12/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(319);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2020/day12/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(286);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day12/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(50157);
    }
}
