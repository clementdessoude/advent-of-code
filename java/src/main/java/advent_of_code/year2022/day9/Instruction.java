package advent_of_code.year2022.day9;

record Instruction(Direction direction, int count) {
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
