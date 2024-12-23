package advent_of_code.year2024.day21;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day21Test {

    Day21 day = new Day21();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day21/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(126384);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day21/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(128962);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day21/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(159684145150108L);
    }
}
