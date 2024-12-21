package advent_of_code.year2024.day21;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolverTest {

    @ParameterizedTest
    @CsvSource({
        "029A, 29",
        "129A, 129",
        "110A, 110"
    })
    void should_compute_numeric_part(String input, int expected) {
        assertThat(new Solver(input).numericPart()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "029A, <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A",
        "980A, <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A",
        "179A, <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A",
        "456A, <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A",
        "379A, <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A",
    })
    void should_compute_shortest_path(String input, String expected) {
        assertThat(new Solver(input).paths()).contains(expected);
    }

    @Test
    void should_compute_shortest_path_for_first_robot() {
        assertThat(new Solver("029A").shortestPathRobot1())
            .containsExactly("<A^A>^^AvvvA", "<A^A^>^AvvvA", "<A^A^^>AvvvA");
    }

    @Test
    void should_compute_shortest_path_for_second_robot() {
        assertThat(new Solver("029A").shortestPathRobot2())
            .contains("v<<A>>^A<A>AvA<^AA>A<vAAA>^A");
    }
}
