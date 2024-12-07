package advent_of_code.year2024.day6;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day6Test {

    Day6 day = new Day6();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day6/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(41);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day6/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(5239);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day6/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(6);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day6/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(1753);
    }
}
