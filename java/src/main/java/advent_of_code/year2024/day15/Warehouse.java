package advent_of_code.year2024.day15;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class Warehouse {

    private final Set<Location> walls;
    private final Set<Location> boxes;
    private Location robot;
    private final Pair<Integer, Integer> dimensions;

    public Warehouse(
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

    public Warehouse move(List<Direction> directions) {
        for (Direction direction : directions) {
            move(direction);
            //            System.out.println(this.toString());
            //            System.out.println();
        }
        return this;
    }

    private void move(Direction direction) {
        var potentialNewRobotDirection = robot.move(direction);
        List<Location> boxToMoves = new ArrayList<>();
        if (isWall(potentialNewRobotDirection)) {
            return;
        }

        if (isEmpty(potentialNewRobotDirection)) {
            robot = potentialNewRobotDirection;
            return;
        }

        var current = potentialNewRobotDirection;
        while (boxes.contains(current)) {
            boxToMoves.add(current);
            current = current.move(direction);
        }

        if (isWall(current)) {
            return;
        }
        robot = potentialNewRobotDirection;
        boxes.remove(boxToMoves.getFirst());
        boxes.add(boxToMoves.getLast().move(direction));
    }

    private boolean isWall(Location location) {
        return walls.contains(location);
    }

    private boolean isEmpty(Location location) {
        return !walls.contains(location) && !boxes.contains(location);
    }

    public long sumOfGpsCoordinates() {
        return boxes.stream().mapToLong(Warehouse::gpsCoordinates).sum();
    }

    private static long gpsCoordinates(Location location) {
        return 100L * location.y() + location.x();
    }

    public String toString() {
        List<List<String>> map = new ArrayList<>(dimensions.second());
        for (int i = 0; i < dimensions.second(); i++) {
            map.add(new ArrayList<>(dimensions.first()));
            for (int j = 0; j < dimensions.first(); j++) {
                map.get(i).add(".");
            }
        }

        walls.forEach(location -> map.get(location.y()).set(location.x(), "#"));
        boxes.forEach(location -> map.get(location.y()).set(location.x(), "O"));
        map.get(robot.y()).set(robot.x(), "@");

        return String.join("\n", map.stream().map(r -> String.join("", r)).toList());
    }
}
