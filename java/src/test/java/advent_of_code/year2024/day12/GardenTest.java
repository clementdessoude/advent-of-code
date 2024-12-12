package advent_of_code.year2024.day12;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Location;
import java.util.List;
import org.junit.jupiter.api.Test;

class GardenTest {

    @Test
    void should_split_into_regions() {
        Garden garden = new Garden(List.of("AAAA"));

        assertThat(garden.regions())
            .hasSize(1)
            .contains(
                Region.of(
                    new Location(0, 0),
                    new Location(1, 0),
                    new Location(2, 0),
                    new Location(3, 0)
                )
            );
    }

    @Test
    void should_split_into_several_regions() {
        Garden garden = new Garden(getLines("year2024/day12/example.txt"));

        assertThat(garden.regions()).hasSize(5);
    }
}
