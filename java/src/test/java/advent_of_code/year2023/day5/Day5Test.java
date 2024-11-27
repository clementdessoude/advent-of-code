package advent_of_code.year2023.day5;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class Day5Test {

    Day5 day = new Day5();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day5/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(35);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day5/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(346433842);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day5/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(46);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day5/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(60294664);
    }
}
