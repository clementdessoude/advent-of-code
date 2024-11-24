package advent_of_code.year2023.day7;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day7Test {

    Day7 day = new Day7();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day7/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(6440);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day7/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(249390788);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day7/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(5905);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day7/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(248750248);
    }
}
