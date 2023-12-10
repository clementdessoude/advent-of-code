
package adventOfCode.day10;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day10Test {

    Day10 day = new Day10();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day10/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void should_solve_blurry_example() {
        List<String> lines = getLines("day10/blurry_example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void should_solve_blurry_second_example() {
        List<String> lines = getLines("day10/blurry_second_example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(8);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day10/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(7030);
    }

    @Test
    void should_solve_part_2_example_1() {
        List<String> lines = getLines("day10/part2_example_1.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void should_solve_part_2_example_2() {
        List<String> lines = getLines("day10/part2_example_2.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void should_solve_part_2_example_3() {
        List<String> lines = getLines("day10/part2_example_3.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(8);
    }

    @Test
    void should_solve_part_2_example_4() {
        List<String> lines = getLines("day10/part2_example_4.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(10);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day10/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(285);
    }
}
