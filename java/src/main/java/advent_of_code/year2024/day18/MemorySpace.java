package advent_of_code.year2024.day18;

import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;

final class MemorySpace {
    private final Map<Integer, Set<Location>> corruptedCoordinatesAfter;
    private final List<Location> fallingBytes;
    private final Pair<Integer, Integer> dimensions;
    
    static MemorySpace atNano(List<Location> fallingBytes, Pair<Integer, Integer> dimension, int nanoseconds) {
        var corruptedCoordinates = fallingBytes.subList(0, nanoseconds);
        var corruptedCoordinatesAfter = Map.of(nanoseconds, Set.copyOf(corruptedCoordinates));
        return new MemorySpace(corruptedCoordinatesAfter, fallingBytes, dimension);
    }

    MemorySpace(List<Location> fallingBytes, Pair<Integer, Integer> dimension, int startingNanoseconds) {
        this.dimensions = dimension;
        var corruptedCoordinates = fallingBytes.subList(0, startingNanoseconds);
        corruptedCoordinatesAfter = new HashMap<>();
        corruptedCoordinatesAfter.put(startingNanoseconds, Set.copyOf(corruptedCoordinates));
        for (int i = startingNanoseconds; i < fallingBytes.size(); i++) {
            Location fallingByte = fallingBytes.get(i);
            var newCorruptedCoordinates = new HashSet<>(corruptedCoordinatesAfter.get(i));
            newCorruptedCoordinates.add(fallingByte);
            corruptedCoordinatesAfter.put(i+1, Set.copyOf(newCorruptedCoordinates));
        }
        this.fallingBytes = fallingBytes;
    }

    private MemorySpace(
        Map<Integer, Set<Location>> corruptedCoordinatesAfter,
        List<Location> fallingBytes,
        Pair<Integer, Integer> dimensions
    ) {
        this.corruptedCoordinatesAfter = corruptedCoordinatesAfter;
        this.fallingBytes = fallingBytes;
        this.dimensions = dimensions;
    }

    Location firstBlockedBy(int startingNanoseconds) {
        for (int i = startingNanoseconds; i < fallingBytes.size(); i++) {
            var shortestPath = shortestPath(i);
            if (shortestPath == null) {
                return fallingBytes.get(i - 1);
            }
        }

        throw new NoSuchElementException();
    }

    int shortestDistanceAfter(int nanoseconds) {
        List<Location> shortestPath = shortestPath(nanoseconds);
        return shortestPath.size() - 1;
    }

    List<Location> shortestPath(int nanoseconds) {
        Map<Location, List<Location>> visited = new HashMap<>();
        Map<Location, List<Location>> available = new HashMap<>();
        available.put(new Location(0,0), List.of(new Location(0,0)));
        while (!available.isEmpty()) {
            var location = next(available);
            var path = available.get(location);
            visited.put(location, path);
            available.remove(location);

            var adjacent = location
                .directAdjacentsInGrid(dimensions)
                .stream()
                .filter(adj -> !visited.containsKey(adj))
                .filter(adj -> !corruptedCoordinatesAfter.get(nanoseconds).contains(adj))
                .map(adj -> {
                    var newPath = new ArrayList<>(path);
                    newPath.add(adj);
                    return Map.entry(adj, newPath);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            available.putAll(adjacent);
        }

        return visited.get(new Location(dimensions.first() - 1, dimensions.second() - 1));
    }

    private static Location next(Map<Location, List<Location>> available) {
        return available
            .entrySet()
            .stream()
            .min(Comparator.comparing(entry -> entry.getValue().size()))
            .map(Map.Entry::getKey)
            .orElseThrow();
    }

    String grid(int nanoseconds) {
        return Display.grid(
            dimensions,
            Map.of(
                corruptedCoordinatesAfter.get(nanoseconds),
                "#"
            )
        );
    }

    String grid(int nanoseconds, List<Location> path) {
        return Display.grid(
            dimensions,
            Map.of(
                corruptedCoordinatesAfter.get(nanoseconds),
                "#",
                path,
                "O"
            )
        );
    }
}
