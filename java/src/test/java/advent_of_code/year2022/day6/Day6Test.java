package advent_of_code.year2022.day6;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day6Test {

    Day6 day = new Day6();

    @ParameterizedTest
    @CsvSource(
        {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb,7",
            "bvwbjplbgvbhsrlpgdmjqwftvncz,5",
            "nppdvjthqldpwncqszvftbrmjlhg,6",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11"
        }
    )
    void should_solve_example(String input, long expected) {
        var result = day.part1(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2022/day6/input.txt");

        var result = day.part1(lines.getFirst());

        assertThat(result).isEqualTo(1566);
    }

    @ParameterizedTest
    @CsvSource(
        {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb,19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz,23",
            "nppdvjthqldpwncqszvftbrmjlhg,23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26"
        }
    )
    void should_solve_part_2_example(String input, long expected) {
        var result = day.part2(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2022/day6/input.txt");

        var result = day.part2(lines.getFirst());

        assertThat(result).isEqualTo(2265);
    }
}
