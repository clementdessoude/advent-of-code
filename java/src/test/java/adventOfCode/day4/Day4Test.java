package adventOfCode.day4;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day4Test {

    Day4 day = new Day4();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day4/example.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(35);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day4/input.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(22674);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day4/example.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(30);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day4/input.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(5747443);
    }

}
