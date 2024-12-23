package advent_of_code.year2024.day21;

import advent_of_code.utils.Location;
import java.util.Map;

/*
    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
 */
class DirectionPad extends Pad {

    private static Pad PAD;

    private DirectionPad() {
        super(
            Map.of(
                "^",
                new Location(1, 0),
                "A",
                new Location(2, 0),
                "<",
                new Location(0, 1),
                "v",
                new Location(1, 1),
                ">",
                new Location(2, 1)
            )
        );
    }

    static Pad getPad() {
        if (PAD == null) {
            PAD = new DirectionPad();
        }
        return PAD;
    }
}
