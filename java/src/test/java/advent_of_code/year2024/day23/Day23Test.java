package advent_of_code.year2024.day23;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day23Test {

    Day23 day = new Day23();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day23/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(7);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day23/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1240);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day23/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day23/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}
