package advent_of_code.year2023.day23;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

class Graph {
    private final Set<Location> nodes = new HashSet<>();
    private final Map<Location, List<Path>> adjacents = new HashMap<>();

    void addNode(Location location) {
        nodes.add(location);
    }

    static Graph from(List<String> lines) {
        var graph = new Graph();

        List<Location> nodes = IntStream
            .range(0, lines.size())
            .mapToObj(i -> IntStream
                .range(0, lines.size())
                .mapToObj(j -> new Location(i, j)))
            .flatMap(Function.identity())
            .filter(l -> lines.get(l.i()).charAt(l.j()) != '#')
            .filter(location -> isIntersection(location, lines))
            .toList();

        nodes.forEach(graph::addNode);

        for (var intersection: nodes) {
            List<Path> paths = pathsFrom(intersection, lines);
            graph.adjacents.put(intersection, paths);
        }

        return graph;
    }

    private static List<Path> pathsFrom(
        Location intersection,
        List<String> lines
    ) {
        return intersection
            .adjacent(lines)
            .stream()
            .filter(l -> lines.get(l.i()).charAt(l.j()) != '#')
            .map(adjacent -> toNextIntersection(intersection, adjacent, lines))
            .toList();
    }

    private static Path toNextIntersection(
        Location intersection,
        Location to,
        List<String> lines
    ) {
        LinkedList<Location> path = new LinkedList<>();
        path.add(intersection);
        Location from = intersection;
        Location next = to;

        while (!isIntersection(next, lines)) {
            path.add(next);

            List<Location> adjacent = next
                .adjacent(lines)
                .stream()
                .filter(l -> lines.get(l.i()).charAt(l.j()) != '#')
                .toList();

            Location first = adjacent.get(0);
            Location second = adjacent.get(1);

            if (first.equals(from)) {
                from = next;
                next = second;
            } else {
                from = next;
                next = first;
            }
        }

        return new Path(next, path.size());
    }

    private static boolean isIntersection(Location location, List<String> lines) {
        if (location.i() == 0 || location.i() == lines.size() - 1) {
            return true;
        }

        return location
            .adjacent(lines)
            .stream()
            .map(l -> lines.get(l.i()).charAt(l.j()))
            .filter(c -> c != '#')
            .count() > 2;
    }

    public int longestPathTo(Location location) {
        List<Location> path = longestPathTo(location, List.of());

        return compute(path);
    }

    private int compute(List<Location> path) {
        Location from = new Location(0, 1);
        int result = 0;
        for (var to: path) {
            Optional<Path> first = adjacents
                .get(from)
                .stream()
                .filter(p -> p.to().equals(to))
                .findFirst();

            if (first.isEmpty()) {
                continue;
            }
            int weight = first
                .orElseThrow()
                .weight();

            result += weight;
            from = to;
        }
        return result;
    }

    public List<Location> longestPathTo(Location location, List<Location> avoid) {
        List<List<Location>> list = adjacents
            .get(location)
            .stream()
            .filter(path -> !avoid.contains(path.to()))
            .map(path -> {
                List<Location> newAvoid = new ArrayList<>(avoid);
                newAvoid.add(path.to());
                List<Location> l = longestPathTo(path.to(), newAvoid);
                l.add(location);
                return l;
            })
            .sorted(Comparator.comparing(this::compute))
            .toList();

        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.getLast();
    }
}
