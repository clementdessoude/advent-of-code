package advent_of_code.year2020.day9;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

    Day9 day = new Day9();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day9/example.txt");

        var result = day.part1(lines, 5);

        assertThat(result).isEqualTo(127);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day9/input.txt");

        var result = day.part1(lines, 25);

        assertThat(result).isEqualTo(69316178);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2020/day9/example.txt");

        var result = day.part2(lines, 5);

        assertThat(result).isEqualTo(62);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day9/input.txt");

        var result = day.part2(lines, 25);

        assertThat(result).isEqualTo(9351526);
    }
}
