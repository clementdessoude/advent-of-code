package advent_of_code.year2023.day3;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

    Day3 day = new Day3();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day3/example.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(4361);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day3/input.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(543867);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day3/example.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(467835);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day3/input.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(79613331);
    }
}
