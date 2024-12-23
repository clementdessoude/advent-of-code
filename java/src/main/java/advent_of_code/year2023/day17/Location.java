package advent_of_code.year2023.day17;

record Location(int i, int j) {
    Location down() {
        return new Location(i + 1, j);
    }

    Location right() {
        return new Location(i, j + 1);
    }

    Location left() {
        return new Location(i, j - 1);
    }

    Location up() {
        return new Location(i - 1, j);
    }
}
