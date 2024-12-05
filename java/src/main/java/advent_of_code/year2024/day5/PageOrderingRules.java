package advent_of_code.year2024.day5;

import java.util.*;

final class PageOrderingRules {

    private final Map<Integer, Set<Integer>> shouldBePrintedAfter = new HashMap<>();

    PageOrderingRules(List<String> input) {
        for (String line : input) {
            if (line.isBlank()) {
                break;
            }
            String[] split = line.split("\\|");
            int first = Integer.parseInt(split[0]);
            int second = Integer.parseInt(split[1]);

            shouldBePrintedAfter.computeIfAbsent(second, _ -> new HashSet<>()).add(first);
        }
    }

    boolean isInCorrectOrder(List<Integer> update) {
        Deque<Integer> stack = new ArrayDeque<>(update);

        while (!stack.isEmpty()) {
            var element = stack.pollFirst();
            if (shouldBePrintedAfter(element, stack)) {
                return false;
            }
        }

        return true;
    }

    private boolean shouldBePrintedAfter(int number, Collection<Integer> others) {
        if (!shouldBePrintedAfter.containsKey(number)) {
            return false;
        }

        for (var other : others) {
            if (shouldBePrintedAfter.get(number).contains(other)) {
                return true;
            }
        }

        return false;
    }
}
