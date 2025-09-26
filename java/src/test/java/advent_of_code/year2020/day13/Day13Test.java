package advent_of_code.year2020.day13;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class Day13Test {

    Day13 day = new Day13();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2020/day13/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(295);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2020/day13/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(2382);
    }

    private static Stream<Arguments> part2Examples() {
        return Stream.of(
            Arguments.of("17,x,13,19", 3417),
            Arguments.of("67,7,59,61", 754018),
            Arguments.of("67,x,7,59,61", 779210),
            Arguments.of("67,7,x,59,61", 1261476),
            Arguments.of("1789,37,47,1889", 1202161486)
        );
    }

    @ParameterizedTest
    @MethodSource("part2Examples")
    void should_solve_part_2_example(String input, int expected) {
        var result = day.part2(List.of("", input));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2020/day13/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(906332393333683L);
    }
}
