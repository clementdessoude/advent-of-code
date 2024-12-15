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

    public Direction opposite() {
        return switch (this) {
            case LEFT -> RIGHT;
            case UP -> DOWN;
            case DOWN -> UP;
            case RIGHT -> LEFT;
        };
    }

    public String arrow() {
        return switch (this) {
            case LEFT -> "<";
            case UP -> "^";
            case DOWN -> "v";
            case RIGHT -> ">";
        };
    }

    public static Direction fromArrow(char c) {
        return switch (c) {
            case '<' -> LEFT;
            case '^' -> UP;
            case 'v' -> DOWN;
            case '>' -> RIGHT;
            default -> throw new IllegalArgumentException();
        };
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }
}
