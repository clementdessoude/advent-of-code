package advent_of_code.year2024.day18;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.List;
import org.junit.jupiter.api.Test;

class MemorySpaceTest {

    @Test
    void should_compute_memory_space_after_a_few_nanoseconds() {
        var space = exampleMemorySpaceAtNano(12);

        assertThat(space.grid(12)).isEqualTo(
            """
            ...#...
            ..#..#.
            ....#..
            ...#..#
            ..#..#.
            .#..#..
            #.#...."""
        );
    }

    @Test
    void should_print_shortest_path_after_nanoseconds() {
        var space = exampleMemorySpaceAtNano(12);

        assertThat(space.grid(12, space.shortestPath(12))).isEqualTo(
            """
            O..#OOO
            O.#OO#O
            OOOO#OO
            ...#OO#
            ..#OO#.
            .#.O#..
            #.#OOOO"""
        );
    }

    @Test
    void should_compute_shortest_path_after_nanoseconds() {
        var space = exampleMemorySpaceAtNano(12);

        assertThat(space.shortestDistanceAfter(12)).isEqualTo(22);
    }

    @Test
    void should_retrieve_first_blocking_step() {
        var space = exampleMemorySpaceStarting(12);
        assertThat(space.firstBlockedBy(12)).isEqualTo(new Location(6, 1));
    }

    private static MemorySpace exampleMemorySpaceStarting(int startingNanoseconds) {
        List<String> lines = getLines("year2024/day18/example.txt");
        var fallingBytes = Day18.parse(lines);
        return new MemorySpace(fallingBytes, new Pair<>(7, 7), startingNanoseconds);
    }

    private static MemorySpace exampleMemorySpaceAtNano(int nanoseconds) {
        List<String> lines = getLines("year2024/day18/example.txt");
        var fallingBytes = Day18.parse(lines);
        return MemorySpace.atNano(fallingBytes, new Pair<>(7, 7), nanoseconds);
    }
}
