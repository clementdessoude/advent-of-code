package advent_of_code.year2022.day9;

import advent_of_code.utils.Location;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

final class Grid {

    private final List<Instruction> instructions;
    private final Set<Location> visitedLocations = new HashSet<>();
    private final List<Location> knotLocations;

    Grid(List<Instruction> instructions, int ropeSize) {
        this.instructions = new ArrayList<>();
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.count(); i++) {
                this.instructions.add(new Instruction(instruction.direction(), 1));
            }
        }
        knotLocations = new ArrayList<>();
        for (int i = 0; i < ropeSize; i++) {
            knotLocations.add(new Location(0, 0));
        }
    }

    int process() {
        visitedLocations.add(knotLocations.getLast());

        for (var instruction : instructions) {
            process(instruction);
            visitedLocations.add(knotLocations.getLast());
        }

        return visitedLocations.size();
    }

    private void process(Instruction instruction) {
        Location newHeadLocation = moveHead(instruction, knotLocations.getFirst());
        knotLocations.set(0, newHeadLocation);
        for (int knotIndex = 0; knotIndex < knotLocations.size() - 1; knotIndex++) {
            Location newTailLocation = moveTail(newHeadLocation, knotLocations.get(knotIndex + 1));
            knotLocations.set(knotIndex + 1, newTailLocation);
            newHeadLocation = newTailLocation;
        }
    }

    private Location moveTail(Location headLocation, Location tailLocation) {
        Location newTailLocation = tailLocation;
        if (headLocation.isAdjacent(tailLocation)) {
            return tailLocation;
        }

        if (headLocation.isOnSameRow(tailLocation)) {
            if (headLocation.isLeftThan(tailLocation, 2)) {
                newTailLocation = headLocation.right();
            } else if (headLocation.isRightThan(tailLocation, 2)) {
                newTailLocation = headLocation.left();
            }
        } else if (headLocation.isOnSameColumn(tailLocation)) {
            if (headLocation.isUpThan(tailLocation, 2)) {
                newTailLocation = headLocation.down();
            } else if (headLocation.isDownThan(tailLocation, 2)) {
                newTailLocation = headLocation.up();
            }
        } else {
            UnaryOperator<Location> verticalDirectionToApply = loc -> loc;
            if (headLocation.isOnSameRow(tailLocation)) {
                verticalDirectionToApply = loc -> loc;
            } else if (headLocation.isUpThan(tailLocation, 1)) {
                verticalDirectionToApply = Location::up;
            } else if (headLocation.isDownThan(tailLocation, 1)) {
                verticalDirectionToApply = Location::down;
            }

            UnaryOperator<Location> horizontalDirectionToApply = loc -> loc;
            if (headLocation.isOnSameColumn(tailLocation)) {
                horizontalDirectionToApply = loc -> loc;
            } else if (headLocation.isLeftThan(tailLocation, 1)) {
                horizontalDirectionToApply = Location::left;
            } else if (headLocation.isRightThan(tailLocation, 1)) {
                horizontalDirectionToApply = Location::right;
            }

            newTailLocation = horizontalDirectionToApply.apply(
                verticalDirectionToApply.apply(tailLocation)
            );
        }

        return newTailLocation;
    }

    private Location moveHead(Instruction instruction, Location location) {
        return switch (instruction.direction()) {
            case LEFT -> location.left(instruction.count());
            case RIGHT -> location.right(instruction.count());
            case UP -> location.up(instruction.count());
            case DOWN -> location.down(instruction.count());
        };
    }
}
