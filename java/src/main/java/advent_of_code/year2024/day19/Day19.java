package advent_of_code.year2024.day19;

import java.util.*;

class Day19 {

    Long part1(List<String> lines) {
        Map<String, Boolean> resolvedPossibles = new HashMap<>();
        List<String> towels = towels(lines.getFirst());
        long possible = 0;
        for (int i = 2; i < lines.size(); i++) {
            if (isPossible(lines.get(i), towels, resolvedPossibles)) {
                possible++;
            }
        }
        return possible;
    }

    private boolean isPossible(String expected, List<String> towels, Map<String, Boolean> resolvedPossibles) {
        if (resolvedPossibles.containsKey(expected)) {
            return resolvedPossibles.get(expected);
        }

        for (String towel : towels) {
            if (
                expected.equals(towel) ||
                (expected.startsWith(towel) && isPossible(expected.substring(towel.length()), towels, resolvedPossibles))
            ) {
                resolvedPossibles.put(expected, true);
                return true;
            }
        }

        resolvedPossibles.put(expected, false);
        return false;
    }

    List<String> towels(String input) {
        return Arrays.stream(input.split(", ")).toList();
    }

    Long part2(List<String> lines) {
        return null;
    }
}
