package advent_of_code.year2024.day22;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SolverTest {

    @ParameterizedTest
    @CsvSource({ "1, 8685429", "10, 4700978", "100, 15273692", "2024, 8667524" })
    void should_compute_result_after_2000_iterations(long input, long expected) {
        assertThat(Solver.solve(input)).isEqualTo(expected);
    }

    @Test
    void should_compute_evolution() {
        assertThat(Solver.evolve(123)).isEqualTo(15887950);
    }
}
