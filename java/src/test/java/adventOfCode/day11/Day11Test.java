
package adventOfCode.day11;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

    Day11 day = new Day11();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day11/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(374);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day11/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(9233514);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day11/example.txt");

        var result = day.part2(lines, 10);

        assertThat(result).isEqualTo(1030);
    }

    @Test
    void should_solve_part_2_example_2() {
        List<String> lines = getLines("day11/example.txt");

        var result = day.part2(lines, 100);

        assertThat(result).isEqualTo(8410);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day11/input.txt");

        var result = day.part2(lines, 1000000);

        assertThat(result).isEqualTo(363293506944L);
    }
}

