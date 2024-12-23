package advent_of_code.year2024.day23;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;

class Day23 {

    Long part1(List<String> lines) {
        var connexions = lines
            .stream()
            .map(line -> line.split("-"))
            .map(split -> new Pair<>(split[0], split[1]))
            .toList();

        Map<String, Set<String>> directAdjacent = new HashMap<>();
        Set<Set<String>> groupsOfThree = new HashSet<>();
        for (var connexion : connexions) {
            Set<String> firstAdjacent = directAdjacent.computeIfAbsent(connexion.first(), k ->
                new HashSet<>()
            );
            Set<String> secondAdjacent = directAdjacent.computeIfAbsent(connexion.second(), k ->
                new HashSet<>()
            );

            var intersection = CollectionUtils.intersection(firstAdjacent, secondAdjacent);
            groupsOfThree.addAll(
                intersection
                    .stream()
                    .map(adj -> Set.of(connexion.first(), connexion.second(), adj))
                    .collect(Collectors.toSet())
            );

            firstAdjacent.add(connexion.second());
            secondAdjacent.add(connexion.first());
        }

        return groupsOfThree
            .stream()
            .filter(group -> group.stream().anyMatch(node -> node.startsWith("t")))
            .count();
    }

    Long part2(List<String> lines) {
        return null;
    }
}
