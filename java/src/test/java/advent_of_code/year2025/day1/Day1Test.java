package advent_of_code.year2025.day1;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

    Day1 day = new Day1();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2025/day1/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2025/day1/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1043);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2025/day1/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(6);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2025/day1/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(5963);
    }
}
