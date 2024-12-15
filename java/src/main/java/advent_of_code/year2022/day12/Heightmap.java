package advent_of_code.year2022.day12;

import advent_of_code.utils.Location;
import java.util.*;

final class Heightmap {

    private final List<List<Integer>> heights;
    private final Location start;
    private final Location end;

    Heightmap(List<String> input) {
        this.heights = new ArrayList<>();
        Location start = null;
        Location end = null;
        for (int i = 0; i < input.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < input.getFirst().length(); j++) {
                char c = input.get(i).charAt(j);
                if (c == 'S') {
                    row.add(0);
                    start = new Location(j, i);
                } else if (c == 'E') {
                    row.add(25);
                    end = new Location(j, i);
                } else {
                    row.add(c - 'a');
                }
            }
            heights.add(row);
        }
        this.start = start;
        this.end = end;
    }

    int shortestPathFromStartingPoint() {
        Map<Location, List<Location>> shortestPathTo = shortestPathWithStartingPoints(
            Set.of(start)
        );
        return shortestPathTo.get(end).size() - 1;
    }

    int shortestPathFromAnyLowPoint() {
        Set<Location> startingPoints = new HashSet<>();
        for (int i = 0; i < heights.size(); i++) {
            for (int j = 0; j < heights.getFirst().size(); j++) {
                if (heights.get(i).get(j) == 0) {
                    startingPoints.add(new Location(j, i));
                }
            }
        }
        Map<Location, List<Location>> shortestPathTo = shortestPathWithStartingPoints(
            startingPoints
        );

        return shortestPathTo.get(end).size() - 1;
    }

    private Map<Location, List<Location>> shortestPathWithStartingPoints(
        Set<Location> startingPoints
    ) {
        Set<Location> visited = new HashSet<>();
        Set<Location> available = new HashSet<>();
        Map<Location, List<Location>> shortestPathTo = new HashMap<>();

        startingPoints.forEach(start -> {
            available.add(start);
            shortestPathTo.put(start, List.of(start));
        });

        while (!available.isEmpty()) {
            var shortestAvailable = available
                .stream()
                .min((a, b) -> shortestPathTo.get(a).size() - shortestPathTo.get(b).size())
                .orElseThrow();

            available.remove(shortestAvailable);
            var adjacents = adjacents(shortestAvailable);
            var path = shortestPathTo.get(shortestAvailable);
            for (var adjacent : adjacents) {
                if (height(adjacent) <= (height(shortestAvailable) + 1)) if (
                    !visited.contains(adjacent)
                ) {
                    available.add(adjacent);
                    shortestPathTo.compute(adjacent, (loc, previousValue) -> {
                        var newPath = new ArrayList<>(path);
                        newPath.add(loc);
                        if (previousValue == null) {
                            return newPath;
                        }

                        return previousValue.size() < path.size() + 1 ? previousValue : newPath;
                    });
                }
            }
            visited.add(shortestAvailable);
        }
        return shortestPathTo;
    }

    private int height(Location location) {
        return heights.get(location.y()).get(location.x());
    }

    private Collection<Location> adjacents(Location shortestAvailable) {
        return shortestAvailable.directAdjacentsInGrid(
            0,
            0,
            heights.getFirst().size(),
            heights.size()
        );
    }
}
