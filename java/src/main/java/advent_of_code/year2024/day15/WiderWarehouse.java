package advent_of_code.year2024.day15;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

final class WiderWarehouse extends Warehouse {

    private final Set<Location> boxesRightPart;

    public WiderWarehouse(
        Set<Location> walls,
        Set<Location> boxes,
        Location robot,
        Pair<Integer, Integer> dimensions
    ) {
        super(walls, boxes, robot, dimensions);
        this.boxesRightPart = boxes.stream().map(Location::right).collect(Collectors.toSet());
    }

    @Override
    public WiderWarehouse move(List<Direction> directions) {
        //        System.out.println("Initial state:");
        //        System.out.println(this.toString());
        for (Direction direction : directions) {
            move(direction);
            //            System.out.println();
            //            System.out.println("Move %s:".formatted(direction.arrow()));
            //            System.out.println(this.toString());
        }
        return this;
    }

    private void move(Direction direction) {
        var potentialNewRobotDirection = robot.move(direction);
        if (isWall(potentialNewRobotDirection)) {
            return;
        }

        if (isEmpty(potentialNewRobotDirection)) {
            robot = potentialNewRobotDirection;
            return;
        }

        var leftPart = boxes.contains(potentialNewRobotDirection)
            ? potentialNewRobotDirection
            : potentialNewRobotDirection.left();
        var movedBoxes = move(leftPart, direction);
        if (movedBoxes.isEmpty()) {
            return;
        }

        robot = potentialNewRobotDirection;
        for (var box : movedBoxes) {
            boxes.remove(box);
            boxesRightPart.remove(box.right());
        }

        for (var box : movedBoxes) {
            boxes.add(box.move(direction));
            boxesRightPart.add(box.right().move(direction));
        }
    }

    private Collection<Location> move(Location leftPart, Direction direction) {
        var newLeftPart = leftPart.move(direction);
        var newRightPart = newLeftPart.right();

        if (isWall(newLeftPart) || isWall(newRightPart)) {
            return Collections.emptySet();
        }

        if (direction.isVertical()) {
            return moveVertical(leftPart, direction, newLeftPart, newRightPart);
        }

        return moveHorizontal(leftPart, direction);
    }

    private Collection<Location> moveHorizontal(Location leftPart, Direction direction) {
        if (isEmpty(direction == Direction.LEFT ? leftPart.left() : leftPart.right().right())) {
            return Set.of(leftPart);
        }

        Location move = leftPart.move(direction).move(direction);
        var movedBoxes = move(move, direction);
        if (movedBoxes.isEmpty()) {
            return movedBoxes;
        }
        var result = new HashSet<>(movedBoxes);
        result.add(leftPart);
        return result;
    }

    private Collection<Location> moveVertical(
        Location leftPart,
        Direction direction,
        Location newLeftPart,
        Location newRightPart
    ) {
        if (isEmpty(newLeftPart) && isEmpty(newRightPart)) {
            return Set.of(leftPart);
        }

        if (boxes.contains(newLeftPart)) {
            var movedBoxes = move(newLeftPart, direction);
            if (movedBoxes.isEmpty()) {
                return movedBoxes;
            }
            var result = new HashSet<>(movedBoxes);
            result.add(leftPart);
            return result;
        }

        var movedBoxes = new HashSet<Location>();
        if (boxesRightPart.contains(newLeftPart)) {
            var movedRightPartBoxes = move(newLeftPart.left(), direction);
            if (movedRightPartBoxes.isEmpty()) {
                return movedRightPartBoxes;
            }
            movedBoxes.addAll(movedRightPartBoxes);
        }

        if (boxes.contains(newRightPart)) {
            var movedLeftPartBoxes = move(newRightPart, direction);
            if (movedLeftPartBoxes.isEmpty()) {
                return movedLeftPartBoxes;
            }
            movedBoxes.addAll(movedLeftPartBoxes);
        }
        var result = new HashSet<>(movedBoxes);
        result.add(leftPart);
        return result;
    }

    private boolean isWall(Location location) {
        return walls.contains(location);
    }

    private boolean isEmpty(Location location) {
        return (
            !walls.contains(location) &&
            !boxes.contains(location) &&
            !boxesRightPart.contains(location)
        );
    }

    public long sumOfGpsCoordinates() {
        return boxes.stream().mapToLong(WiderWarehouse::gpsCoordinates).sum();
    }

    private static long gpsCoordinates(Location location) {
        return 100L * location.y() + location.x();
    }

    public String toString() {
        return Display.grid(
            dimensions,
            Map.of(walls, "#", boxes, "[", boxesRightPart, "]", List.of(robot), "@")
        );
    }
}
