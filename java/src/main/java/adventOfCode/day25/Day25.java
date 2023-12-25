
package adventOfCode.day25;

import java.util.*;

public class Day25 {

    public int part1(List<String> lines) {
        Map<String, Set<String>> connexions = parse(lines);

        Set<String> currentGroup = new HashSet<>();
        Set<String> connectedGroup = new HashSet<>();

        String currentNode = connexions.keySet().stream().findAny().orElseThrow();
        currentGroup.add(currentNode);
        for (var n: connexions.get(currentNode)) {
            if (!currentGroup.contains(n)) {
                connectedGroup.add(n);
            }
        }

        do {
            currentNode = next(connexions, connectedGroup, currentGroup);
            currentGroup.add(currentNode);
            connectedGroup.remove(currentNode);
            for (var n: connexions.get(currentNode)) {
                if (!currentGroup.contains(n)) {
                    connectedGroup.add(n);
                }
            }
        } while (connexionCount(currentGroup, connectedGroup, connexions) > 3);

        return (connexions.size() - currentGroup.size()) * currentGroup.size();
    }

    private long connexionCount(
        Set<String> currentGroup,
        Set<String> connectedGroup,
        Map<String, Set<String>> connexions
    ) {
        return currentGroup
            .stream()
            .map(connexions::get)
            .flatMap(Collection::stream)
            .filter(connectedGroup::contains)
            .count();
    }

    private String next(
        Map<String, Set<String>> connexions,
        Set<String> connectedNodes,
        Set<String> currentGroup
    ) {
        long max = -1;
        String maxNode = null;
        for (var node: connectedNodes) {
            if (currentGroup.contains(node)) {
                continue;
            }
            var count = connexions
                .get(node)
                .stream()
                .filter(currentGroup::contains).count();
            if (count > max) {
                max = count;
                maxNode = node;
            }
        }
        return maxNode;
    }

    private static Map<String, Set<String>> parse(List<String> lines) {
        Map<String, Set<String>> connexions = new HashMap<>();

        for (var row: lines) {
            var split = row.split(": ");

            var first = split[0];
            var connected = split[1].split(" ");

            connexions.putIfAbsent(first, new HashSet<>());

            for (String node : connected) {
                connexions.putIfAbsent(node, new HashSet<>());
                connexions.get(first).add(node);
                connexions.get(node).add(first);
            }
        }

        return connexions;
    }

    public Long part2(List<String> lines) {
        return null;
    }
}

