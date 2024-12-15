package advent_of_code.year2024.day15;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class StandardWarehouse extends Warehouse {

    public StandardWarehouse(
        Set<Location> walls,
        Set<Location> boxes,
        Location robot,
        Pair<Integer, Integer> dimensions
    ) {
        super(walls, boxes, robot, dimensions);
    }

    @Override
    public StandardWarehouse move(List<Direction> directions) {
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

    public String toString() {
        return Display.grid(dimensions, Map.of(walls, "#", boxes, "O", List.of(robot), "@"));
    }
}
