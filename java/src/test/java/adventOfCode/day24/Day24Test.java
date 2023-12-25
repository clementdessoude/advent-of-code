
package adventOfCode.day24;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day24Test {

    Day24 day = new Day24();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day24/example.txt");

        var result = day.part1(lines, new Area(7L, 27L));

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day24/input.txt");

        var result = day.part1(lines,  new Area(200_000_000_000_000L, 400_000_000_000_000L));

        assertThat(result).isEqualTo(29142);
    }

    @Test
    @Disabled
    void should_solve_part_2_example() {
        List<String> lines = getLines("day24/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    @Disabled
    void should_solve_part_2() {
        List<String> lines = getLines("day24/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

