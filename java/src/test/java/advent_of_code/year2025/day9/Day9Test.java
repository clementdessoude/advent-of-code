package advent_of_code.year2025.day9;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day9Test {

    Day9 day = new Day9();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2025/day9/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(50);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2025/day9/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(4772103936L);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2025/day9/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(24);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2025/day9/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}
