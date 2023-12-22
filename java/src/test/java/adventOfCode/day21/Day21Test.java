
package adventOfCode.day21;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day21Test {

    Day21 day = new Day21();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day21/example.txt");

        var result = day.part1(lines, 6);

        assertThat(result).isEqualTo(16);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day21/input.txt");

        var result = day.part1(lines, 64);

        assertThat(result).isEqualTo(3841);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day21/example.txt");

        var result = day.part2(lines, 100);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day21/input.txt");

        var result = day.part2(lines, 26501365);

        assertThat(result).isEqualTo(977);
    }
}

