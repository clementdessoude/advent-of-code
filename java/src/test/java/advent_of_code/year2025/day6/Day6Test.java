package advent_of_code.year2025.day6;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day6Test {

    Day6 day = new Day6();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2025/day6/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(4277556);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2025/day6/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(3968933219902L);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2025/day6/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(3263827);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2025/day6/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(6019576291014L);
    }
}
