package advent_of_code.year2024.day21;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DirectionPadTest {

    @Test
    void should_compute_shortest_move() {
        var pad = DirectionPad.getPad();
        var shortestMove = pad.shortestMoves("A", "<");

        assertThat(shortestMove).contains("v<<", "<v<");
    }
}
