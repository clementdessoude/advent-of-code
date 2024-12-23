package advent_of_code.year2024.day21;

import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Pair;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolverTest {

    @ParameterizedTest
    @CsvSource({ "029A, 29", "129A, 129", "110A, 110" })
    void should_compute_numeric_part(String input, int expected) {
        assertThat(new Solver(input).numericPart()).isEqualTo(expected);
    }

    @Test
    void should_compute_shortest_path_for_first_robot() {
        String code = "029A";
        Solver solver = new Solver(code);
        assertThat(solver.shortestPath("A" + code, NumericPad.getPad())).containsExactlyInAnyOrder(
            "<A^A>^^AvvvA",
            "<A^A^>^AvvvA",
            "<A^A^^>AvvvA"
        );
    }

    @ParameterizedTest
    @CsvSource({
        "3,<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A",
        "2,v<<A>>^A<A>AvA<^AA>A<vAAA>^A",
        "1,<A^A>^^AvvvA"
    })
    void test(int robotCount, String expected) {
        HashMap<Pair<String, String>, Map<Integer, Long>> cache = new HashMap<>();
        long actual = new Solver("029A").minPath(robotCount, cache);
        assertThat(actual).isEqualTo(expected.length());
    }
}
