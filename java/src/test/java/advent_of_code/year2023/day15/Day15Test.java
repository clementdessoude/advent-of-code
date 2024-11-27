package advent_of_code.year2023.day15;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day15Test {

    Day15 day = new Day15();

    public static Stream<Arguments> providePart1Examples() {
        return Stream.of(
            Arguments.of("rn=1", 30),
            Arguments.of("cm-", 253),
            Arguments.of("qp=3", 97),
            Arguments.of("cm=2", 47),
            Arguments.of("qp-", 14),
            Arguments.of("pc=4", 180),
            Arguments.of("ot=9", 9)
        );
    }

    @ParameterizedTest
    @MethodSource("providePart1Examples")
    void should_solve_part1_example(String input, int expected) {
        var result = Day15.hash(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2023/day15/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1320);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2023/day15/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(511257);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("year2023/day15/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(145);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2023/day15/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(239484);
    }
}
