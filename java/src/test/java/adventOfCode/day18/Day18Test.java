
package adventOfCode.day18;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class Day18Test {

    Day18 day = new Day18();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day18/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(62);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day18/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(42859);
    }

    public static Stream<Arguments> providePart2Examples() {
        return Stream.of(
            Arguments.of("R 6 (#70c710)", new Day18.Instruction('R', 461937)),
            Arguments.of("D 5 (#0dc571)", new Day18.Instruction('D', 56407)),
            Arguments.of("L 2 (#5713f0)", new Day18.Instruction('R', 356671)),
            Arguments.of("D 2 (#d2c081)", new Day18.Instruction('D', 863240)),
            Arguments.of("R 2 (#59c680)", new Day18.Instruction('R', 367720)),
            Arguments.of("D 2 (#411b91)", new Day18.Instruction('D', 266681)),
            Arguments.of("L 5 (#8ceee2)", new Day18.Instruction('L', 577262)),
            Arguments.of("U 2 (#caa173)", new Day18.Instruction('U', 829975)),
            Arguments.of("L 1 (#1b58a2)", new Day18.Instruction('L', 112010)),
            Arguments.of("U 2 (#caa171)", new Day18.Instruction('D', 829975)),
            Arguments.of("R 2 (#7807d2)", new Day18.Instruction('L', 491645)),
            Arguments.of("U 3 (#a77fa3)", new Day18.Instruction('U', 686074)),
            Arguments.of("L 2 (#015232)", new Day18.Instruction('L', 5411)),
            Arguments.of("U 2 (#7a21e3)", new Day18.Instruction('U', 500254))
        );
    }

    @ParameterizedTest
    @MethodSource("providePart2Examples")
    void should_parse_instructions(String row, Day18.Instruction expected) {
        var result = Day18.parsePart2(row);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_2_example_cubic() {
        List<String> lines = getLines("day18/cubic.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(461938L * 461938L);
    }

    @ParameterizedTest
    @CsvSource({
        "one, 41",
        "two, 26",
        "three, 48",
        "four, 50",
    })
    void should_solve_part_2_example_one(String fileName, long expected) {
        List<String> lines = getLines("day18/%s.txt".formatted(fileName));

        var instructions = lines
            .stream()
            .map(r -> {
                String[] split = r.split(" ");
                return new Day18.Instruction(split[0].charAt(0), Integer.parseInt(split[1]));
            })
            .toList();

        var result = Day18.count(instructions);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day18/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(952408144115L);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day18/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}

