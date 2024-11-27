package advent_of_code.year2023.day25;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day25Test {

    Day25 day = new Day25();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day25/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(54);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day25/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(562978);
    }
}
