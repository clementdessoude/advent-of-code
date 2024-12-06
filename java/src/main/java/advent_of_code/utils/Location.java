package advent_of_code.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Location(int x, int y) {
    public Location up() {
        return up(1);
    }

    public Location up(int times) {
        return new Location(x, y - times);
    }

    public Location down() {
        return down(1);
    }

    public Location down(int times) {
        return new Location(x, y + times);
    }

    public Location left() {
        return left(1);
    }

    public Location left(int times) {
        return new Location(x - times, y);
    }

    public Location right() {
        return right(1);
    }

    public Location right(int times) {
        return new Location(x + times, y);
    }

    public boolean isOnSameRow(Location other) {
        return y == other.y;
    }

    public boolean isOnSameColumn(Location other) {
        return x == other.x;
    }

    public boolean isLeftThan(Location other, int times) {
        return x + times <= other.x;
    }

    public boolean isRightThan(Location other, int times) {
        return x - times >= other.x;
    }

    public boolean isUpThan(Location other, int times) {
        return y + times <= other.y;
    }

    public boolean isDownThan(Location other, int times) {
        return y + times >= other.y;
    }

    public boolean isAdjacent(Location other) {
        return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1;
    }

    public static Stream<Location> stream(int minX, int minY, int maxX, int maxY) {
        return IntStream.range(minX, maxX)
            .mapToObj(x -> IntStream.range(minY, maxY).mapToObj(y -> new Location(x, y)))
            .flatMap(x -> x);
    }

    public Collection<Location> adjacentsInGrid(int minX, int minY, int maxX, int maxY) {
        return Stream.of(
            this.up(),
            this.up().right(),
            this.up().left(),
            this.left(),
            this.right(),
            this.down(),
            this.down().right(),
            this.down().left()
        )
            .filter(loc -> loc.isInGrid(minX, minY, maxX, maxY))
            .collect(Collectors.toSet());
    }

    public boolean isInGrid(int minX, int minY, int maxX, int maxY) {
        return this.x() >= minX && this.x() < maxX && this.y() >= minY && this.y() < maxY;
    }

    public Location move(Direction direction) {
        return switch (direction) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }
}
