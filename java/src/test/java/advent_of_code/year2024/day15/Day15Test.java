package advent_of_code.year2024.day15;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class Day15Test {

    Day15 day = new Day15();

    public static Stream<Arguments> part1() {
        return Stream.of(
            Arguments.of("year2024/day15/example.txt", 10092),
            Arguments.of("year2024/day15/small_example.txt", 2028),
            Arguments.of("year2024/day15/input.txt", 1430439)
        );
    }

    @ParameterizedTest
    @MethodSource("part1")
    void should_solve_part1(String inputFile, int expected) {
        List<String> lines = getLines(inputFile);

        var result = day.part1(lines);

        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> part2() {
        return Stream.of(
            Arguments.of("year2024/day15/example.txt", 9021),
            Arguments.of("year2024/day15/small_example_part_2.txt", 618),
            Arguments.of("year2024/day15/horizontal_move_blocked.txt", 606),
            Arguments.of("year2024/day15/right_move_blocked.txt", 618),
            Arguments.of("year2024/day15/input.txt", 1458740)
        );
    }

    @ParameterizedTest
    @MethodSource("part2")
    void should_solve_part2(String inputFile, int expected) {
        List<String> lines = getLines(inputFile);

        var result = day.part2(lines);

        assertThat(result).isEqualTo(expected);
    }
}
