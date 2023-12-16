package adventOfCode.day16;

import java.util.Optional;

record Position(int x, int y) {

    Optional<Position> next(BeamDirection direction, Grid grid) {
        return switch (direction) {
            case UP -> x() == 0
                ? Optional.empty()
                : Optional.of(up());
            case DOWN -> x() == grid.lineNumber() - 1
                ? Optional.empty()
                : Optional.of(down());
            case LEFT -> y() == 0
                ? Optional.empty()
                : Optional.of(left());
            case RIGHT -> y() == grid.columnNumber() - 1
                ? Optional.empty()
                : Optional.of(right());
        };
    }

    Position up() {
        return new Position(x - 1, y);
    }

    Position down() {
        return new Position(x + 1, y);
    }

    Position left() {
        return new Position(x, y - 1);
    }

    Position right() {
        return new Position(x, y + 1);
    }
}
