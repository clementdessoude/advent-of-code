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
    private Location headLocation = new Location(0, 0);
    private Location tailLocation = new Location(0, 0);

    Grid(List<Instruction> instructions) {
        this.instructions = new ArrayList<>();
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.count(); i++) {
                this.instructions.add(new Instruction(instruction.direction(), 1));
            }
        }
    }

    int process() {
        visitedLocations.add(tailLocation);

        for (var instruction : instructions) {
            process(instruction);
        }

        return visitedLocations.size();
    }

    private void process(Instruction instruction) {
        switch (instruction.direction()) {
            case LEFT -> {
                headLocation = headLocation.left(instruction.count());
            }
            case RIGHT -> {
                headLocation = headLocation.right(instruction.count());
            }
            case UP -> {
                headLocation = headLocation.up(instruction.count());
            }
            case DOWN -> {
                headLocation = headLocation.down(instruction.count());
            }
        }

        if (headLocation.isAdjacent(tailLocation)) {
            return;
        }

        if (headLocation.isOnSameRow(tailLocation)) {
            if (headLocation.isLeftThan(tailLocation, 2)) {
                tailLocation = headLocation.right();
            } else if (headLocation.isRightThan(tailLocation, 2)) {
                tailLocation = headLocation.left();
            }
        } else if (headLocation.isOnSameColumn(tailLocation)) {
            if (headLocation.isUpThan(tailLocation, 2)) {
                tailLocation = headLocation.down();
            } else if (headLocation.isDownThan(tailLocation, 2)) {
                tailLocation = headLocation.up();
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

            tailLocation = horizontalDirectionToApply.apply(
                verticalDirectionToApply.apply(tailLocation)
            );
        }

        visitedLocations.add(tailLocation);
    }
}
