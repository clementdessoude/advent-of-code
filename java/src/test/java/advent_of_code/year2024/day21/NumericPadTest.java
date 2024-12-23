package advent_of_code.year2024.day21;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NumericPadTest {

    @Test
    void should_compute_shortest_move() {
        var pad = NumericPad.getPad();
        var shortestMove = pad.shortestMoves("A", "4");

        assertThat(shortestMove).hasSize(5).contains("^<^<", "^^<<");
    }
}
