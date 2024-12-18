package advent_of_code.year2024.day18;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Pair;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day18Test {

    Day18 day = new Day18();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day18/example.txt");

        var result = day.part1(lines, new Pair<>(7, 7), 12);

        assertThat(result).isEqualTo(22);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day18/input.txt");

        var result = day.part1(lines, new Pair<>(71, 71), 1024);

        assertThat(result).isEqualTo(292);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2024/day18/example.txt");

        var result = day.part2(lines, new Pair<>(7, 7), 12);

        assertThat(result).isEqualTo("6,1");
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day18/input.txt");

        var result = day.part2(lines, new Pair<>(71, 71), 3005);

        assertThat(result).isEqualTo("58,44");
    }
}
