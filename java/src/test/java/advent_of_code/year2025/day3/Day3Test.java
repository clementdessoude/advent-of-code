package advent_of_code.year2025.day3;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

    Day3 day = new Day3();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2025/day3/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(357);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2025/day3/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(17207);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2025/day3/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(3121910778619L);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2025/day3/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(170997883706617L);
    }
}
