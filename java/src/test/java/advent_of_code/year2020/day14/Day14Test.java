package advent_of_code.year2020.day14;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day14Test {

    Day14 day = new Day14();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day14/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(165);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day14/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(9615006043476L);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2020/day14/example_part2.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(208);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day14/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}
