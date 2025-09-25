package advent_of_code.year2020.day11;

import advent_of_code.utils.Location;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

class Zone {

    static final int FLOOR = 0;
    static final int SEAT = 1;
    static final int OCCUPIED = 2;

    private final Map<Location, Integer> grid;
    private final Map<Location, Set<Location>> visibleSeatByLocation;

    private final int maxX;
    private final int maxY;
    private final int minX;
    private final int minY;
    private final boolean isStable;

    Zone(Map<Location, Integer> grid, int maxX, int maxY) {
        this.grid = grid;
        this.minX = 0;
        this.minY = 0;
        this.maxX = maxX;
        this.maxY = maxY;
        this.isStable = false;
        this.visibleSeatByLocation = visibleSeatByLocation(grid);
    }

    Zone(Map<Location, Integer> grid, int maxX, int maxY, boolean isStable) {
        this.grid = grid;
        this.minX = 0;
        this.minY = 0;
        this.maxX = maxX;
        this.maxY = maxY;
        this.isStable = isStable;
        this.visibleSeatByLocation = visibleSeatByLocation(grid);
    }

    Zone run(boolean isPart2) {
        var newGrid = new HashMap<Location, Integer>();
        var isStable = true;
        for (var location : grid.keySet()) {
            int newOccupation = isPart2 ? runPart2(location) : run(location);
            boolean hasChanged = newOccupation != grid.get(location);
            newGrid.put(location, newOccupation);

            if (hasChanged) {
                isStable = false;
            }
        }

        return new Zone(newGrid, maxX, maxY, isStable);
    }

    boolean isStable() {
        return isStable;
    }

    public long occupiedCount() {
        return grid.keySet().stream().filter(this::isOccupied).count();
    }

    private int run(Location location) {
        if (isFloor(location)) {
            return FLOOR;
        }

        var adjacent = location.adjacentsInGrid(minX, minY, maxX, maxY);
        if (adjacent.stream().allMatch(this::isFree)) {
            return OCCUPIED;
        }
        if (adjacent.stream().filter(this::isOccupied).count() >= 4) {
            return SEAT;
        }

        return grid.get(location);
    }

    private int runPart2(Location location) {
        if (isFloor(location)) {
            return FLOOR;
        }

        var visibles = visibleSeatByLocation.get(location);
        if (visibles.stream().allMatch(this::isFree)) {
            return OCCUPIED;
        }
        if (visibles.stream().filter(this::isOccupied).count() >= 5) {
            return SEAT;
        }

        return grid.get(location);
    }

    private boolean isFloor(Location location) {
        return grid.get(location) == FLOOR;
    }

    private boolean isOccupied(Location location) {
        return grid.get(location) == OCCUPIED;
    }

    private boolean isFree(Location location) {
        return grid.get(location) == SEAT || grid.get(location) == FLOOR;
    }

    private Map<Location, Set<Location>> visibleSeatByLocation(Map<Location, Integer> grid) {
        var result = new HashMap<Location, Set<Location>>();
        for (var location : grid.keySet()) {
            if (isFloor(location)) {
                continue;
            }
            result.put(location, visibleSeats(grid, location));
        }
        return result;
    }

    private Set<Location> visibleSeats(Map<Location, Integer> grid, Location location) {
        return Stream.<UnaryOperator<Location>>of(
            Location::up,
            Location::down,
            Location::left,
            Location::right,
            loc -> loc.up().left(),
            loc -> loc.up().right(),
            loc -> loc.down().left(),
            loc -> loc.down().right()
        )
            .map(move -> visibleSeat(location, move))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    private @Nullable Location visibleSeat(Location location, UnaryOperator<Location> move) {
        var next = move.apply(location);
        while (next.isInGrid(minX, minY, maxX, maxY)) {
            if (!isFloor(next)) {
                return next;
            }
            next = move.apply(next);
        }

        return null;
    }

    public void print() {
        for (int y = 0; y < maxY; y++) {
            var row = new StringBuilder();
            for (int x = 0; x < maxX; x++) {
                var value =
                    switch (grid.get(new Location(x, y))) {
                        case FLOOR -> ".";
                        case OCCUPIED -> "#";
                        case SEAT -> "L";
                        case null -> throw new IllegalStateException(
                            "Invalid location " + new Location(x, y)
                        );
                        default -> throw new IllegalStateException(
                            "Unexpected value: " + grid.get(new Location(x, y))
                        );
                    };
                row.append(value);
            }
            System.out.println(row);
        }
        System.out.println();
    }
}
