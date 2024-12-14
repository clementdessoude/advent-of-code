package advent_of_code.year2024.day14;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Pair;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day14Test {

    Day14 day = new Day14();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day14/example.txt");

        var result = day.part1(lines, new Pair<>(11, 7));

        assertThat(result).isEqualTo(12);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day14/input.txt");

        var result = day.part1(lines, new Pair<>(101, 103));

        assertThat(result).isEqualTo(218619324);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day14/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(result);
    }
}
