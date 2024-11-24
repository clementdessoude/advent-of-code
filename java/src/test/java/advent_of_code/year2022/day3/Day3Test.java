package advent_of_code.year2022.day3;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

    Day3 day = new Day3();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2022/day3/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(157);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2022/day3/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(7597);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2022/day3/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(70);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2022/day3/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2607);
    }

    @Test
    void should_get_priority() {
        assertThat(day.priority('a')).isEqualTo(1);
        assertThat(day.priority('z')).isEqualTo(26);
        assertThat(day.priority('A')).isEqualTo(27);
        assertThat(day.priority('Z')).isEqualTo(52);
    }
}
