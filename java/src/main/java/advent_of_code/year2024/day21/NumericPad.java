package advent_of_code.year2024.day21;

import advent_of_code.utils.Location;
import java.util.Map;

/*
+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+
 */
class NumericPad extends Pad {

    private static Pad PAD;

    private NumericPad() {
        super(Map.ofEntries(
            Map.entry("7", new Location(0, 0)),
            Map.entry("8", new Location(1, 0)),
            Map.entry("9", new Location(2, 0)),
            Map.entry("4", new Location(0, 1)),
            Map.entry("5", new Location(1, 1)),
            Map.entry("6", new Location(2, 1)),
            Map.entry("1", new Location(0, 2)),
            Map.entry("2", new Location(1, 2)),
            Map.entry("3", new Location(2, 2)),
            Map.entry("0", new Location(1, 3)),
            Map.entry("A", new Location(2, 3))
        ));
    }

    static Pad getPad() {
        if (PAD == null) {
            PAD = new NumericPad();
        }
        return PAD;
    }
}
