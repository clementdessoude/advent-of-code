
package adventOfCode.day25;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day25Test {

    Day25 day = new Day25();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day25/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(54);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day25/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(562978);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day25/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day25/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

