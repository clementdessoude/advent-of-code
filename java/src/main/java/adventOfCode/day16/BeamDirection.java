package adventOfCode.day16;

public enum BeamDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
}
