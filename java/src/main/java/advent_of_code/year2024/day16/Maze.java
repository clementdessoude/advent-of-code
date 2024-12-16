package advent_of_code.year2024.day16;

import advent_of_code.utils.Direction;
import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;

final class Maze {

    private final List<String> maze;
    private final Location start;
    private final Location end;

    Maze(List<String> maze) {
        this.maze = maze;
        Location start = null;
        Location end = null;
        for (int y = 0; y < maze.size(); y++) {
            for (int x = 0; x < maze.get(y).length(); x++) {
                if (maze.get(y).charAt(x) == 'S') {
                    start = new Location(x, y);
                } else if (maze.get(y).charAt(x) == 'E') {
                    end = new Location(x, y);
                }
            }
        }
        this.start = start;
        this.end = end;
    }

    long shortestDistance() {
        var djikstra = djikstra();
        var distances = djikstra.first();
        var paths = djikstra.second();

        var result = Direction
            .stream()
            .map(direction -> new Pair<>(end, direction))
            .min(Comparator.comparing(pair -> distances.getOrDefault(
                pair,
                Integer.MAX_VALUE
            )))
            .orElseThrow();

//        display(maze, paths.get(result).stream().findAny().orElseThrow());
        return distances.get(result);
    }

    long numberOfTilesOnShortestPath() {
        var djikstra = djikstra();
        var distances = djikstra.first();
        var paths = djikstra.second();

        var shortestPath = Direction
            .stream()
            .map(direction -> new Pair<>(end, direction))
            .mapToInt(pair -> distances.getOrDefault(
                pair,
                Integer.MAX_VALUE
            ))
            .min()
            .orElseThrow();

        var locationsOnShortestPath = Direction
            .stream()
            .map(direction -> new Pair<>(end, direction))
            .filter(pair -> distances.getOrDefault(pair, Integer.MAX_VALUE) == shortestPath)
            .map(paths::get)
            .flatMap(Collection::stream)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

//        display(maze, locationsOnShortestPath);

        return locationsOnShortestPath.size();
    }

    private Pair<
        Map<Pair<Location, Direction>, Integer>,
        Map<Pair<Location, Direction>, Set<List<Location>>>
    > djikstra() {
        Set<Pair<Location, Direction>> visited = new HashSet<>();
        Set<Pair<Location, Direction>> availables = new HashSet<>();
        Map<Pair<Location, Direction>, Integer> distances = new HashMap<>();
        Map<Pair<Location, Direction>, Set<List<Location>>> paths = new HashMap<>();

        Direction.stream().forEach(direction -> {
            var pair = new Pair<>(start, direction);
            availables.add(pair);
            distances.put(pair, 1000);
            paths.put(pair, Set.of(List.of(start)));
        });

        while (!availables.isEmpty()) {
            var pair = getNext(distances, availables);
            visited.add(pair);
            availables.remove(pair);

            var location = pair.first();
            var currentDirection = pair.second();
            var currentDistance = distances.get(pair);

            currentDirection
                .allButOpposite()
                .forEach(direction -> {
                    var adjacent = location.move(direction);
                    var adjacentPair = new Pair<>(adjacent, direction);
                    if (visited.contains(adjacentPair) || isWall(adjacent)) {
                        return;
                    }

                    availables.add(adjacentPair);
                    var valueToAdd = direction == currentDirection ? 1 : 1001;
                    var actualNextDistance = distances.getOrDefault(
                        adjacentPair,
                        Integer.MAX_VALUE
                    );

                    Set<List<Location>> pathsToGetToAdjacent = paths
                        .get(pair)
                        .stream()
                        .map(path -> {
                            var newPath1 = new ArrayList<>(path);
                            newPath1.add(adjacent);
                            return newPath1;
                        })
                        .collect(Collectors.toSet());
                    if (currentDistance + valueToAdd == actualNextDistance) {
                        paths.computeIfPresent(adjacentPair, (k, v) -> {
                            var updated = new HashSet<>(v);
                            updated.addAll(pathsToGetToAdjacent);
                            return updated;
                        });
                    }

                    if (currentDistance + valueToAdd < actualNextDistance) {
                        distances.put(
                            adjacentPair,
                            currentDistance + valueToAdd
                        );
                        paths.put(
                            adjacentPair,
                            pathsToGetToAdjacent
                        );
                    }
                });
        }
        return new Pair<>(distances, paths);
    }

    private void display(List<String> maze, Collection<Location> locations) {
        List<List<String>> lines = new ArrayList<>(maze.size());
        for (int i = 0; i < maze.size(); i++) {
            var row = new ArrayList<String>(maze.get(i).length());
            for (int j = 0; j < maze.get(i).length(); j++) {
                String value = locations.contains(new Location(j, i))
                    ? "O"
                    : String.valueOf(maze.get(i).charAt(j));
                row.add(value);
            }
            lines.add(row);
        }

        Display.display(lines);
    }

    private Pair<Location, Direction> getNext(
        Map<Pair<Location, Direction>, Integer> distances,
        Set<Pair<Location, Direction>> available
    ) {
        return distances
            .entrySet()
            .stream()
            .filter(entry -> available.contains(entry.getKey()))
            .min(Comparator.comparing(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .orElseThrow();
    }

    private boolean isWall(Location location) {
        return charAt(location) == '#';
    }

    private char charAt(Location location) {
        return maze.get(location.y()).charAt(location.x());
    }
}
