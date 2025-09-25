package advent_of_code.year2020.day10;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day10Test {

    Day10 day = new Day10();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day10/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(35);
    }

    @Test
    void should_solve_example_2() {
        List<String> lines = getLines("year2020/day10/example_2.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(220);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day10/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(2232);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2020/day10/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(8);
    }

    @Test
    void should_solve_part_2_example_2() {
        List<String> lines = getLines("year2020/day10/example_2.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(19208);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day10/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(173625106649344L);
    }
}
