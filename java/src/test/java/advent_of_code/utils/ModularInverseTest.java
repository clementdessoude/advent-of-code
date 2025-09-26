package advent_of_code.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ModularInverseTest {

    @Test
    void should_solve_system() {
        List<Pair<Long, Long>> values = List.of(
            new Pair<>(5L, 7L),
            new Pair<>(3L, 11L),
            new Pair<>(10L, 13L)
        );

        assertThat(ModularInverse.solve(values)).isEqualTo(894);
    }
}
