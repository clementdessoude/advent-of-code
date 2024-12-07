package advent_of_code.year2024.day7;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ConcatenationTest {

    Concatenation concatenation = Concatenation.CONCATENATION;

    @Test
    void shouldReduceResult() {
        Long result = 12345L;
        Long element = 345L;

        Long reduced = concatenation.reducedResult(result, element);

        assertThat(reduced).isEqualTo(12);
    }
}
