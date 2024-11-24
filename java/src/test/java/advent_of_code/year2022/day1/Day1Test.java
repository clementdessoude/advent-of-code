package advent_of_code.year2022.day1;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

    Day1 day = new Day1();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day1/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(114);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day1/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1980437560);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day1/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day1/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

