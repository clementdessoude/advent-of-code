package advent_of_code.year2023.day2;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day2Test {

    Day2 day = new Day2();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day2/example.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(8);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day2/input.txt");

        Integer result = day.part1(lines);

        assertThat(result).isEqualTo(2149);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day2/example.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(2286);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day2/input.txt");

        Integer result = day.part2(lines);

        assertThat(result).isEqualTo(71274);
    }
}
