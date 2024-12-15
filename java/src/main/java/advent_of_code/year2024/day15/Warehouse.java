package advent_of_code.year2024.day15;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.List;
import java.util.Set;

abstract class Warehouse {

    protected final Set<Location> walls;
    protected final Set<Location> boxes;
    protected Location robot;
    protected final Pair<Integer, Integer> dimensions;

    Warehouse(
        Set<Location> walls,
        Set<Location> boxes,
        Location robot,
        Pair<Integer, Integer> dimensions
    ) {
        this.walls = walls;
        this.boxes = boxes;
        this.robot = robot;
        this.dimensions = dimensions;
    }

    public abstract Warehouse move(List<Direction> directions);

    public long sumOfGpsCoordinates() {
        return boxes.stream().mapToLong(Warehouse::gpsCoordinates).sum();
    }

    private static long gpsCoordinates(Location location) {
        return 100L * location.y() + location.x();
    }
}
