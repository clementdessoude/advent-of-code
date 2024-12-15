package advent_of_code.year2024.day15;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day15 {

    Long part1(List<String> lines) {
        var blankLine = lines.indexOf("");
        var warehouse = warehouse(lines, blankLine);
        var directions = getDirections(lines, blankLine);

        return warehouse.move(directions).sumOfGpsCoordinates();
    }

    private static List<Direction> getDirections(List<String> lines, int blankLine) {
        var directions = new ArrayList<Direction>();

        for (int i = blankLine + 1; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                directions.add(Direction.fromArrow(c));
            }
        }
        return directions;
    }

    private static StandardWarehouse warehouse(List<String> lines, int blankLine) {
        Set<Location> walls = new HashSet<>();
        Set<Location> boxes = new HashSet<>();
        Location robot = null;
        for (int i = 0; i < blankLine; i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                if (c == '#') {
                    walls.add(new Location(j, i));
                } else if (c == 'O') {
                    boxes.add(new Location(j, i));
                } else if (c == '@') {
                    robot = new Location(j, i);
                }
            }
        }
        return new StandardWarehouse(
            walls,
            boxes,
            robot,
            new Pair<>(lines.getFirst().length(), blankLine)
        );
    }

    Long part2(List<String> lines) {
        var blankLine = lines.indexOf("");
        var directions = getDirections(lines, blankLine);
        return widerWarehouse(lines, blankLine).move(directions).sumOfGpsCoordinates();
    }

    private static WiderWarehouse widerWarehouse(List<String> lines, int blankLine) {
        Set<Location> walls = new HashSet<>();
        Set<Location> boxes = new HashSet<>();
        Location robot = null;
        for (int i = 0; i < blankLine; i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                if (c == '#') {
                    walls.add(new Location(2 * j, i));
                    walls.add(new Location(2 * j + 1, i));
                } else if (c == 'O') {
                    boxes.add(new Location(2 * j, i));
                } else if (c == '@') {
                    robot = new Location(2 * j, i);
                }
            }
        }
        return new WiderWarehouse(
            walls,
            boxes,
            robot,
            new Pair<>(2 * lines.getFirst().length(), blankLine)
        );
    }
}
