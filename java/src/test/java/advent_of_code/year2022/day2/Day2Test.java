package advent_of_code.year2022.day2;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day2Test {

    Day2 day = new Day2();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2022/day2/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(15);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2022/day2/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(13924);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2022/day2/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(12);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2022/day2/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(13448);
    }
}
