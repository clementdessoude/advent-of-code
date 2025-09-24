package advent_of_code.year2024.day25;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class KeyTest {

    @Test
    void should_parse_key() {
        var description =
            """
            .....
            #....
            #....
            #...#
            #.#.#
            #.###
            #####
            """;
        var key = Key.of(Arrays.stream(description.split("\\n")).toList());

        assertThat(key.heights()).containsExactly(5, 0, 2, 1, 3);
    }
}
