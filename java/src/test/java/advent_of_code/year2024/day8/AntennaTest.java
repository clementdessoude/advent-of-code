package advent_of_code.year2024.day8;

import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Location;
import java.util.List;
import org.junit.jupiter.api.Test;

class AntennaTest {

    @Test
    void shouldComputeAntinodes() {
        var first = new Antenna("a", new Location(4, 3));
        var second = new Antenna("a", new Location(5, 5));

        var result = Antenna.antinodes(List.of(first, second));

        assertThat(result).containsExactlyInAnyOrder(new Location(3, 1), new Location(6, 7));
    }
}
