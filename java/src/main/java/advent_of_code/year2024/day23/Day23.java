package advent_of_code.year2024.day23;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day23 {

    Long part1(List<String> lines) {
        var connexions = lines
            .stream()
            .map(line -> line.split("-"))
            .map(split -> new Pair<>(split[0], split[1]))
            .toList();

        Set<Set<String>> groupsOfThree = groupsOfThree(connexions);

        return groupsOfThree
            .stream()
            .filter(group -> group.stream().anyMatch(node -> node.startsWith("t")))
            .count();
    }

    private static Set<Set<String>> groupsOfThree(List<Pair<String, String>> connexions) {
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
        return groupsOfThree;
    }

    private static Set<Set<String>> biggerGroup(
        List<Pair<String, String>> connexionPairs,
        Set<Set<String>> connexions,
        Set<Set<String>> smallerGroup,
        Set<String> nodes
    ) {
        Set<Set<String>> biggerGroups = new HashSet<>();
        Set<Set<String>> visited = new HashSet<>();

        Map<String, Set<Set<String>>> belongsToGroup = new HashMap<>();
        for (var node : nodes) {
            belongsToGroup.put(node, new HashSet<>());
        }

        for (var group : smallerGroup) {
            for (var node : group) {
                belongsToGroup.get(node).add(group);
            }
        }

        for (var connexion : connexionPairs) {
            var first = connexion.first();
            var second = connexion.second();

            addToBiggerGroup(connexions, belongsToGroup.get(first), second, biggerGroups, visited);
            addToBiggerGroup(connexions, belongsToGroup.get(second), first, biggerGroups, visited);
        }

        return biggerGroups;
    }

    private static void addToBiggerGroup(
        Set<Set<String>> connexions,
        Set<Set<String>> groupOfFirst,
        String second,
        Set<Set<String>> biggerGroups,
        Set<Set<String>> visited
    ) {
        for (var group : groupOfFirst) {
            if (group.contains(second) || visited.contains(group)) {
                continue;
            }
            boolean isLinkedToEachMember = group
                .stream()
                .allMatch(node -> areLinked(node, second, connexions));
            if (isLinkedToEachMember) {
                var biggerGroup = new HashSet<>(group);
                visited.add(group);
                biggerGroup.add(second);
                biggerGroups.add(biggerGroup);
            }
        }
    }

    private static boolean areLinked(String first, String second, Set<Set<String>> connexions) {
        return connexions.contains(Set.of(first, second));
    }

    String part2(List<String> lines) {
        var connexionPairs = lines
            .stream()
            .map(line -> line.split("-"))
            .map(split -> new Pair<>(split[0], split[1]))
            .toList();

        var connexions = connexionPairs
            .stream()
            .map(pair -> Set.of(pair.first(), pair.second()))
            .collect(Collectors.toSet());

        var nodes = connexionPairs
            .stream()
            .flatMap(pair -> Stream.of(pair.first(), pair.second()))
            .collect(Collectors.toSet());

        Set<Set<String>> smallerGroup = groupsOfThree(connexionPairs);
//        System.out.println("Size: " + smallerGroup.size());
        Set<Set<String>> nextGroup = biggerGroup(connexionPairs, connexions, smallerGroup, nodes);
        int i = 4;
        while (!nextGroup.isEmpty()) {
//            System.out.println(i++);
//            System.out.println("Size: " + nextGroup.size());
            smallerGroup = nextGroup;
            nextGroup = biggerGroup(connexionPairs, connexions, nextGroup, nodes);
        }

        var biggestGroup = smallerGroup.stream().findFirst().orElseThrow();

        return biggestGroup.stream().sorted(String::compareTo).collect(Collectors.joining(","));
    }
}
