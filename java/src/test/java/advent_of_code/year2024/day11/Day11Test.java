package advent_of_code.year2024.day11;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

    Day11 day = new Day11();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day11/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(55312);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day11/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(189547);
    }

    @Test
    void should_solve_one_blink() {
        var result = Day11.resolve("125 17", 1);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day11/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(65601038650482L);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day11/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(224577979481346L);
    }
}
