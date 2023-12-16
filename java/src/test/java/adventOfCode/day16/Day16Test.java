
package adventOfCode.day16;

import static adventOfCode.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day16Test {

    Day16 day = new Day16();

    public static Stream<Arguments> providePart1Example() {
        return Stream.of(
            Arguments.of(
                """
                 ...
                 """,
                """
                >>>
                """
            ),
            Arguments.of(
                """
                 .-.
                 """,
                """
                >->
                """
            ),
            Arguments.of(
                """
                 .|.
                 ...
                 """,
                """
                >|.
                .v.
                """
            ),
            Arguments.of(
                """
                 .\\.
                 ...
                 """,
                """
                >\\.
                .v.
                """
            ),
            Arguments.of(
                """
                 .\\.
                 ...
                 ./.
                 """,
                """
                >\\.
                .v.
                </.
                """
            ),
            Arguments.of(
                """
                .|.\\
                ....
                .\\./
                """,
                """
                >|<\\
                .v.^
                .\\>/
                """
            ),
            Arguments.of(
                """
                 .|.
                 ...
                 .-.
                 ...
                 """,
                """
                >|.
                .v.
                <->
                ...
                """
            ),
            Arguments.of(
                """
                 .|.
                 ...
                 .|.
                 ...
                 """,
                """
                >|.
                .v.
                .|.
                .v.
                """
            )
        );
    }

    @ParameterizedTest
    @MethodSource("providePart1Example")
    void should_show_test_directions(String input, String expected) {
        List<String> lines = input.lines().toList();
        List<String> directions = expected.lines().filter(s -> !s.isBlank()).toList();

        var grid = new Grid(lines);
        grid.solve(new Position(0, -1), BeamDirection.RIGHT);

        assertThat(grid.showDirections()).isEqualTo(directions);
    }

    @Test
    void should_show_example_energized() {
        List<String> lines = getLines("day16/example.txt");
        List<String> directions = getLines("day16/example_energized.txt");

        var grid = new Grid(lines);
        grid.solve(new Position(0, -1), BeamDirection.RIGHT);

        assertThat(grid.showEnergized()).isEqualTo(directions);
    }

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day16/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(46);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day16/input.txt");

        var grid = new Grid(lines);
        grid.solve(new Position(0, -1), BeamDirection.RIGHT);
        System.out.println(
            String.join("\n", grid.showDirections())
        );

        assertThat(grid.energizedCount(new Position(0, -1),
                                       BeamDirection.RIGHT)).isEqualTo(8901);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day16/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(51);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day16/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(9064);
    }
}

