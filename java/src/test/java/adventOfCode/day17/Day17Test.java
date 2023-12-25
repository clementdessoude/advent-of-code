
package adventOfCode.day17;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class Day17Test {

    Day17 day = new Day17();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day17/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(102);
    }

    @Test
    void should_solve_new_example() {
        List<String> lines = getLines("day17/new_example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(15);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day17/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1265);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day17/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day17/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

