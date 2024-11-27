package advent_of_code.year2023.day8;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day8Test {

    Day8 day = new Day8();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day8/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(6);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day8/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(20569);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day8/example_part2.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(6);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day8/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(21_366_921_060_721L);
    }
}
