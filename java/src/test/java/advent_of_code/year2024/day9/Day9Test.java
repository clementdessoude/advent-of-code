package advent_of_code.year2024.day9;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

    Day9 day = new Day9();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day9/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1928);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day9/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(6367087064415L);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day9/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2858);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day9/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(6390781891880L);
    }
}
