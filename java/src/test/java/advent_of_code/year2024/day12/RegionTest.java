package advent_of_code.year2024.day12;

import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Location;
import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    void should_compute_perimeter() {
        var region = Region.of(
            new Location(0, 0),
            new Location(1, 0),
            new Location(2, 0),
            new Location(3, 0)
        );

        assertThat(region.fencePrice()).isEqualTo(40);
    }

    @Test
    void should_compute_number_of_sides() {
        var region = Region.of(
            new Location(0, 0),
            new Location(1, 0),
            new Location(2, 0),
            new Location(3, 0)
        );

        assertThat(region.numberOfSides()).isEqualTo(4);
    }

    @Test
    void should_compute_number_of_sides_one_tile() {
        var region = Region.of(new Location(0, 0));

        assertThat(region.numberOfSides()).isEqualTo(4);
    }

    @Test
    void should_compute_number_of_sides_L() {
        var region = Region.of(new Location(0, 0), new Location(1, 0), new Location(0, 1));

        assertThat(region.numberOfSides()).isEqualTo(6);
    }
}
