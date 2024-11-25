package advent_of_code.year2022.day4;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day4Test {

    Day4 day = new Day4();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2022/day4/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2022/day4/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(515);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2022/day4/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2022/day4/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(883);
    }
}
