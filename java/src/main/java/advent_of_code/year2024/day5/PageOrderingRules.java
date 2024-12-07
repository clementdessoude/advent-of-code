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

            shouldBePrintedAfter.computeIfAbsent(second, __ -> new HashSet<>()).add(first);
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

    boolean isNotInCorrectOrder(List<Integer> update) {
        return !isInCorrectOrder(update);
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

    public List<Integer> reorder(List<Integer> update) {
        Map<Integer, Integer> positions = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            if (positions.containsKey(update.get(i))) {
                throw new IllegalArgumentException("update should contains unique elements");
            }

            positions.put(update.get(i), i);
        }

        Comparator<Integer> comparator = (a, b) -> {
            if (shouldBePrintedAfter.getOrDefault(a, new HashSet<>()).contains(b)) {
                return 1;
            }
            if (shouldBePrintedAfter.getOrDefault(b, new HashSet<>()).contains(a)) {
                return -1;
            }
            return positions.get(a) - positions.get(b);
        };

        return update.stream().sorted(comparator).toList();
    }
}
