package advent_of_code.year2024.day16;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day16Test {

    Day16 day = new Day16();

    public static Stream<Arguments> part1() {
        return Stream.of(
            Arguments.of("year2024/day16/example.txt", 7036),
            Arguments.of("year2024/day16/example_2.txt", 11048),
            Arguments.of("year2024/day16/input.txt", 85432)
        );
    }

    @ParameterizedTest
    @MethodSource("part1")
    void should_solve_part1(String input, int expected) {
        List<String> lines = getLines(input);

        var result = day.part1(lines);

        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> part2() {
        return Stream.of(
            Arguments.of("year2024/day16/example.txt", 45),
            Arguments.of("year2024/day16/example_2.txt", 64),
            Arguments.of("year2024/day16/input.txt", 465)
        );
    }

    @ParameterizedTest
    @MethodSource("part2")
    void should_solve_part_2(String input, int expected) {
        List<String> lines = getLines(input);

        var result = day.part2(lines);

        assertThat(result).isEqualTo(expected);
    }
}
