package adventOfCode.day1;

import static adventOfCode.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

    Day1 day1 = new Day1();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day1/example.txt");

        Integer result = day1.solve(lines);

        assertThat(result).isEqualTo(142);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day1/input.txt");

        Integer result = day1.solve(lines);

        assertThat(result).isEqualTo(54304);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day1/example_part2.txt");

        Integer result = day1.solvePart2(lines);

        assertThat(result).isEqualTo(281);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day1/input.txt");

        Integer result = day1.solvePart2(lines);

        assertThat(result).isEqualTo(54406);
    }
}
