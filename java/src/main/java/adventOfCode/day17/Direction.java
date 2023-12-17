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
}
