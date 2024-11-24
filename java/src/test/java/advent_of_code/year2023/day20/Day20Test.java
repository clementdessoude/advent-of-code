
package advent_of_code.year2023.day20;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day20Test {

    Day20 day = new Day20();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day20/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(32000000);
    }

    @Test
    void should_solve_example_1() {
        List<String> lines = getLines("day20/example_2.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(11687500);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day20/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(898557000);
    }

    @Test
    @Disabled
    void should_solve_part_2_example() {
        List<String> lines = getLines("day20/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    @Disabled
    void should_solve_part_2() {
        List<String> lines = getLines("day20/input.txt");

        var result = day.part2(lines);
        var t = 2_742_049_843_200L;
//        var result = day.test();

        var s = "011111111111111111010111011111111111111111111111";
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                k++;
            }
        }
        assertThat(k).isEqualTo(12);
//        assertThat(result).isEqualTo(977);
    }
}

