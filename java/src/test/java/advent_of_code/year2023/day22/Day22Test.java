package advent_of_code.year2023.day22;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day22Test {

    Day22 day = new Day22();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day22/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(5);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day22/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(488);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day22/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(7);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day22/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(79465L);
    }
}
