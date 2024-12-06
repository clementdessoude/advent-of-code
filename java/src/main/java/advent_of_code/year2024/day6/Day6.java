package advent_of_code.year2024.day6;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day6 {

    int part1(List<String> lines) {
        var parse = parse(lines);

        var obstacles = parse.obstacles();
        var guardPosition = parse.startingPosition();
        var direction = Direction.UP;

        int maxY = lines.size();
        int maxX = lines.getFirst().length();

        Set<Location> visited = new HashSet<>();
        visited.add(guardPosition);

        while (guardPosition.isInGrid(0, 0, maxX, maxY)) {
            var nextPosition = guardPosition.move(direction);
            if (obstacles.contains(nextPosition)) {
                direction = direction.turnRight();
            } else {
                guardPosition = nextPosition;
                visited.add(guardPosition);
            }
        }

        return visited.size() - 1;
    }

    private record Parse(Location startingPosition, Set<Location> obstacles) {}

    private Parse parse(List<String> lines) {
        Set<Location> obstacles = new HashSet<>();
        Location startingPosition = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                if (lines.get(y).charAt(x) == '#') {
                    obstacles.add(new Location(x, y));
                } else if (lines.get(y).charAt(x) == '^') {
                    startingPosition = new Location(x, y);
                }
            }
        }

        return new Parse(startingPosition, obstacles);
    }

    Long part2(List<String> lines) {
        return null;
    }
}
