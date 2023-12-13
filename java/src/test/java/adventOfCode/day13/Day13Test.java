
package adventOfCode.day13;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day13Test {

    Day13 day = new Day13();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day13/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(405);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day13/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(30872);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day13/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day13/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

