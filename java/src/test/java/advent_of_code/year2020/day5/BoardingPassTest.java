package advent_of_code.year2020.day5;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardingPassTest {

    @Test
    void shouldRetrieveRowId() {
        var boardingPass = new BoardingPass("FBFBBFFRLR");

        assertThat(boardingPass.rowId()).isEqualTo(44);
    }

    @Test
    void shouldRetrieveColumnId() {
        var boardingPass = new BoardingPass("FBFBBFFRLR");

        assertThat(boardingPass.columnId()).isEqualTo(5);
    }

    @Test
    void shouldRetrieveSeatId() {
        var boardingPass = new BoardingPass("FBFBBFFRLR");

        assertThat(boardingPass.seatId()).isEqualTo(357);
    }
}
