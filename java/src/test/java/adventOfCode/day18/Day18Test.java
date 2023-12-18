
package adventOfCode.day18;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day18Test {

    Day18 day = new Day18();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day18/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(62);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day18/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(42859);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day18/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day18/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

