package advent_of_code.year2020.day11;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

    Day11 day = new Day11();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day11/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(37);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day11/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(2152);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2020/day11/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(26);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day11/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(1937);
    }
}
