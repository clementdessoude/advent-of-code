package advent_of_code.year2024.day19;

import java.util.*;

class Day19 {

    Long part1(List<String> lines) {
        Map<String, Integer> resolvedPossibles = new HashMap<>();
        List<String> towels = towels(lines.getFirst());
        long possible = 0;
        for (int i = 2; i < lines.size(); i++) {
            if (isPossible(lines.get(i), towels, resolvedPossibles)) {
                possible++;
            }
        }
        return possible;
    }


    private boolean isPossible(String expected, List<String> towels, Map<String, Integer> resolvedPossibles) {
        return numberOfArrangements(expected, towels, resolvedPossibles) > 0;
    }

    private int numberOfArrangements(String expected, List<String> towels, Map<String, Integer> resolvedPossibles) {
        if (resolvedPossibles.containsKey(expected)) {
            return resolvedPossibles.get(expected);
        }

        for (String towel : towels) {
            if (towel.equals(expected)) {
                resolvedPossibles.compute(expected, (k, v) -> v == null ? 1 : v + 1);
            }
            if (
                expected.startsWith(towel) &&
                    isPossible(expected.substring(towel.length()), towels, resolvedPossibles)
            ) {
                var count = resolvedPossibles.get(expected.substring(towel.length()));
                resolvedPossibles.compute(expected, (k, v) -> v == null ? count : v + count);
            }
        }

        resolvedPossibles.computeIfAbsent(expected, k -> 0);
        return resolvedPossibles.get(expected);
    }

    List<String> towels(String input) {
        return Arrays.stream(input.split(", ")).toList();
    }

    Long part2(List<String> lines) {
        Map<String, Integer> resolvedPossibles = new HashMap<>();
        List<String> towels = towels(lines.getFirst());
        long possible = 0;
        for (int i = 2; i < lines.size(); i++) {
            if (isPossible(lines.get(i), towels, resolvedPossibles)) {
                possible += resolvedPossibles.getOrDefault(
                    lines.get(i),
                    0
                );
            }
        }
        return possible;
    }
}
