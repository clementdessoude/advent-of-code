package advent_of_code.year2024.day21;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Pad {

    private final Map<Pair<String, String>, Collection<String>> shortestMoves;
    private final Map<String, Location> pad;
    private final Map<Location, String> reversePad;

    Pad(Map<String, Location> pad) {
        this.pad = pad;
        this.reversePad = pad
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        shortestMoves = getShortestMoves(pad, reversePad);
    }

    private static Map<Pair<String, String>, Collection<String>> getShortestMoves(
        Map<String, Location> pad,
        Map<Location, String> reversePad
    ) {
        var pairs = CollectionUtils.pairOf(pad.keySet());

        return pairs
            .stream()
            .flatMap(p -> Stream.of(p, new Pair<>(p.second(), p.first())))
            .collect(Collectors.toMap(
                Function.identity(),
                move -> getShortestMovesDescription(move, pad, reversePad)
            ));
    }

    private static Collection<String> getShortestMovesDescription(
        Pair<String, String> move,
        Map<String, Location> pad,
        Map<Location, String> reversePad
    ) {
        Collection<List<Location>> moves = getShortestMoves(move, pad, reversePad);
        return moves.stream().map(Pad::toDescription).collect(Collectors.toSet());
    }

    private static Collection<List<Location>> getShortestMoves(
        Pair<String, String> move,
        Map<String, Location> pad,
        Map<Location, String> reversePad
    ) {
        Map<String, Set<List<Location>>> visited = new HashMap<>();
        Map<String, Set<List<Location>>> available = new HashMap<>();
        available.put(move.first(), Set.of(List.of(pad.get(move.first()))));
        while (!available.isEmpty()) {
            var next = available
                .entrySet()
                .stream()
                .min(Comparator.comparing(e -> e.getValue().stream().findFirst().orElseThrow().size()))
                .get();
            var key = next.getKey();
            var paths = next.getValue();
            var location = pad.get(key);

            available.remove(key);
            visited.put(key, paths);

            Map<Location, Set<List<Location>>> updatedPaths = location
                .directAdjacentsInGrid()
                .stream()
                .filter(reversePad::containsKey)
                .filter(loc -> !visited.containsKey(reversePad.get(loc)))
                .flatMap(adjacent -> paths
                    .stream()
                    .map(path -> {
                        var newPath = new ArrayList<>(path);
                        newPath.add(adjacent);
                        return newPath;
                    })
                )
                .collect(Collectors.toMap(
                    List::getLast,
                    l -> {
                        Set<List<Location>> set = new HashSet<>();
                        set.add(l);
                        return set;
                    },
                    CollectionUtils::concat
                ));

            updatedPaths.forEach((loc, updatedPath) -> {
               available.putIfAbsent(reversePad.get(loc), Set.of());
               available.computeIfPresent(reversePad.get(loc), (__, v) -> {
                   int firstSize = v.stream().findFirst().map(List::size).orElse(Integer.MAX_VALUE);
                   int secondSize = updatedPath.stream().findFirst().orElseThrow().size();
                   if (firstSize == secondSize) {
                       return CollectionUtils.concat(v, updatedPath);
                   } else if (firstSize < secondSize) {
                       return v;
                   }
                   return updatedPath;
               });
            });
        }

        return visited.get(move.second());
    }

    private static String toDescription(List<Location> locations) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < locations.size() - 1; i++) {
            builder.append(locations.get(i + 1).fromAdjacent(locations.get(i)).arrow());
        }
        return builder.toString();
    }

    public Collection<String> shortestMoves(String from, String to) {
        if (from.equals(to)) {
            return List.of("");
        }
        return shortestMoves.get(new Pair<>(from, to));
    }
}
