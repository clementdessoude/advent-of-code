package advent_of_code.year2023.day11;

public record Galaxy(int i, int j) {

    int distanceTo(Galaxy other) {
        return Math.abs(i - other.i) + Math.abs(j - other.j);
    }
}
