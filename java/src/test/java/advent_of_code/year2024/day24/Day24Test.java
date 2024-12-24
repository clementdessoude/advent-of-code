package advent_of_code.year2024.day24;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day24Test {

    Day24 day = new Day24();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day24/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(2024);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day24/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(57632654722854L);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day24/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day24/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}
