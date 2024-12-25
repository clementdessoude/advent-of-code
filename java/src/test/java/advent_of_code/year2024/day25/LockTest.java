package advent_of_code.year2024.day25;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class LockTest {

    @Test
    void should_parse_lock() {
        var description = """
            #####
            .####
            .####
            .####
            .#.#.
            .#...
            .....
            """;
        var lock = Lock.of(Arrays.stream(description.split("\\n")).toList());

        assertThat(lock.heights()).containsExactly(0,5,3,4,3);
    }
}
