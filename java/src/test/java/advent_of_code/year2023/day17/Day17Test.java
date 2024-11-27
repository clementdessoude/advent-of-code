package advent_of_code.year2023.day17;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class Day17Test {

    Day17 day = new Day17();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day17/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(102);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day17/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1263);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day17/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(94);
    }

    @Test
    void should_solve_part_2_example_2() {
        List<String> lines = getLines("year2023/day17/example_2.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(71);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day17/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(1411);
    }
}
