package adventOfCode.day17;

enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
        };
    }

    static Direction direction(Location from, Location to) {
        if (to.i() == from.i()) {
            if (to.j() > from.j()) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (to.i() > from.i()) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
    }

}
