package advent_of_code.utils;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }
}
