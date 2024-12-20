package advent_of_code.year2024.day20;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day20Test {

    Day20 day = new Day20();

    @ParameterizedTest
    @CsvSource(
        {
            "2, 44",
            "4, 30",
            "6, 16",
            "8, 14",
            "10, 10",
            "12, 8",
            "20, 5",
            "36, 4",
            "38, 3",
            "40, 2",
            "64, 1"
        }
    )
    void should_solve_example(int threshold, int expected) {
        List<String> lines = getLines("year2024/day20/example.txt");

        var result = day.part1(lines, threshold);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day20/input.txt");

        var result = day.part1(lines, 100);

        assertThat(result).isEqualTo(1360);
    }

    @ParameterizedTest
    @CsvSource(
        {
            "50, 285",
            "52, 253",
            "54, 222",
            "56, 193",
            "58, 154",
            "60, 129",
            "62, 106",
            "64, 86",
            "66, 67",
            "68, 55",
            "70, 41",
            "72, 29",
            "74, 7",
            "76, 3"
        }
    )
    void should_solve_part_2_example(int threshold, int expected) {
        List<String> lines = getLines("year2024/day20/example.txt");

        var result = day.part2(lines, threshold);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day20/input.txt");

        var result = day.part2(lines, 100);

        assertThat(result).isEqualTo(1005476);
    }
}
