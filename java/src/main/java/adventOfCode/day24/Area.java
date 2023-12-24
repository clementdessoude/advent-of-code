package adventOfCode.day24;

record Area(
    long min,
    long max
) {
    public boolean doesNotContains(double crossedX, double crossedY) {
        return crossedX < min || crossedX > max || crossedY < min || crossedY > max;
    }
}
