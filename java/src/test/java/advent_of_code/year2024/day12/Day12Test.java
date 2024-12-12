package advent_of_code.year2024.day12;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class Day12Test {

    Day12 day = new Day12();

    @ParameterizedTest
    @CsvSource(
        """
        year2024/day12/example.txt, 140
        year2024/day12/example2.txt, 772
        year2024/day12/example3.txt, 1930
        year2024/day12/input.txt, 1_477_762
        """
    )
    void should_solve_example(String inputFile, int expected) {
        List<String> lines = getLines(inputFile);

        var result = day.part1(lines);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(
        """
        year2024/day12/example.txt, 80
        year2024/day12/example2.txt, 436
        yearyear2024/day12/input.txt, 923480
        """
    )
    void should_solve_part_2_example(String inputFile, int expected) {
        List<String> lines = getLines(inputFile);

        var result = day.part2(lines);

        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> provided() {
        return Stream.of(
            Arguments.of(
                """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE
                """,
                236
            ),
            Arguments.of(
                """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE
                """,
                1206
            ),
            Arguments.of(
                """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA
                """,
                368
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provided")
    void should_solve_part_2_example_E(String input, int expected) {
        List<String> lines = List.of(input.split("\n"));

        var result = day.part2(lines);

        assertThat(result).isEqualTo(expected);
    }
}
