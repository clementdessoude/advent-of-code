package advent_of_code.year2024.day10;

import static advent_of_code.utils.CollectionUtils.notIn;

import advent_of_code.utils.Location;
import java.util.*;
import java.util.stream.Collectors;

final class TopographicMap {

    private final List<List<Integer>> heights;
    private final List<Location> trailHeads = new ArrayList<>();

    TopographicMap(List<String> rows) {
        heights = rows
            .stream()
            .map(row -> Arrays.stream(row.split("")).map(Integer::parseInt).toList())
            .toList();

        for (int y = 0; y < heights.size(); y++) {
            for (int x = 0; x < heights.getFirst().size(); x++) {
                if (heights.get(y).get(x) == 0) {
                    trailHeads.add(new Location(x, y));
                }
            }
        }
    }

    public long totalScore() {
        return trailHeads.stream().mapToLong(this::score).sum();
    }

    long score(Location trailHead) {
        Set<Location> visited = new HashSet<>();
        Set<Location> available = new HashSet<>();
        available.add(trailHead);
        while (!available.isEmpty()) {
            Location next = available.iterator().next();
            available.remove(next);
            visited.add(next);

            var adjacents = possibleAdjacents(next);
            available.addAll(notIn(adjacents, visited));
        }

        return visited.stream().map(this::height).filter(h -> h == 9).count();
    }

    public long totalRating() {
        return trailHeads.stream().mapToLong(this::rating).sum();
    }

    long rating(Location trailHead) {
        Set<List<Location>> visited = new HashSet<>();
        Set<List<Location>> available = new HashSet<>();
        available.add(List.of(trailHead));
        while (!available.isEmpty()) {
            List<Location> currentPath = available.iterator().next();
            available.remove(currentPath);

            if (height(currentPath.getLast()) == 9) {
                visited.add(currentPath);
            }

            var adjacents = possibleAdjacents(currentPath.getLast());

            available.addAll(
                adjacents
                    .stream()
                    .map(adj -> {
                        var newPath = new ArrayList<>(currentPath);
                        newPath.add(adj);
                        return newPath;
                    })
                    .toList()
            );
        }

        return visited.size();
    }

    private Collection<Location> possibleAdjacents(Location position) {
        int currentHeight = height(position);
        return position
            .directAdjacentsInGrid(0, 0, heights.getFirst().size(), heights.size())
            .stream()
            .filter(p -> height(p) == (currentHeight + 1))
            .toList();
    }

    private int height(Location position) {
        return heights.get(position.y()).get(position.x());
    }
}
