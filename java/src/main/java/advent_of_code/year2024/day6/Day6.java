package advent_of_code.year2024.day6;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;

class Day6 {

    int part1(List<String> lines) {
        var visited = resolve(lines).first();
        return visited.size() - 1;
    }

    private record Parse(Location startingPosition, Set<Location> obstacles) {}

    int part2(List<String> lines) {
        Set<Location> obstaclesThatWouldCreateLoops = resolve(lines).second();

        return obstaclesThatWouldCreateLoops.size();
    }

    private Pair<Set<Location>, Set<Location>> resolve(List<String> lines) {
        var parse = parse(lines);

        var obstacles = parse.obstacles();
        var guardPosition = parse.startingPosition();
        var direction = Direction.UP;

        int maxY = lines.size();
        int maxX = lines.getFirst().length();

        Set<Location> possibleLoops = new HashSet<>();
        Set<Location> visited = new HashSet<>();
        visited.add(guardPosition);

        while (guardPosition.isInGrid(0, 0, maxX, maxY)) {
            var nextPosition = guardPosition.move(direction);

            if (obstacles.contains(nextPosition)) {
                direction = direction.turnRight();
                continue;
            }

            HashSet<Location> updatedObstacles = new HashSet<>(obstacles);
            updatedObstacles.add(nextPosition);
            if (
                !visited.contains(nextPosition) &&
                wouldCreateLoop(guardPosition, maxX, maxY, direction.turnRight(), updatedObstacles)
            ) {
                possibleLoops.add(nextPosition);
            }
            guardPosition = nextPosition;
            visited.add(guardPosition);
        }

        possibleLoops.remove(parse.startingPosition());

        return new Pair<>(visited, possibleLoops);
    }

    private boolean wouldCreateLoop(
        Location guardPosition,
        int maxX,
        int maxY,
        Direction direction,
        Collection<Location> obstacles
    ) {
        var guard = guardPosition;
        Map<Location, Set<Direction>> visitedObstacles = new HashMap<>();
        while (guard.isInGrid(0, 0, maxX, maxY)) {
            var nextPosition = guard.move(direction);
            boolean isNextPositionAnObstacle = obstacles.contains(nextPosition);
            if (isNextPositionAnObstacle) {
                if (visitedObstacles.getOrDefault(nextPosition, Set.of()).contains(direction)) {
                    return true;
                }

                visitedObstacles.computeIfAbsent(nextPosition, k -> new HashSet<>()).add(direction);
                direction = direction.turnRight();
            } else {
                guard = nextPosition;
            }
        }

        return false;
    }

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
}
