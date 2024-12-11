package advent_of_code.year2024.day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Stone {

    private static final Map<Long, Map<Integer, Long>> cache = new HashMap<>();
    private final long value;

    Stone(long value) {
        this.value = value;
    }

    public List<Stone> evolve() {
        if (value == 0) {
            return List.of(new Stone(1));
        }

        String stringValue = String.valueOf(value);
        if (stringValue.length() % 2 == 0) {
            long first = Long.parseLong(stringValue.substring(0, stringValue.length() / 2));
            long second = Long.parseLong(stringValue.substring(stringValue.length() / 2));
            return List.of(new Stone(first), new Stone(second));
        }

        return List.of(new Stone(2024 * value));
    }

    public long countHowManyStonesHasProducedAfter(int blinkCount) {
        if (blinkCount == 0) {
            return 1;
        }

        if (cache.getOrDefault(value, new HashMap<>()).containsKey(blinkCount)) {
            return cache.getOrDefault(value, new HashMap<>()).get(blinkCount);
        }

        var stones = evolve();
        var count = stones
            .stream()
            .mapToLong(stone -> stone.countHowManyStonesHasProducedAfter(blinkCount - 1))
            .sum();
        cache.putIfAbsent(value, new HashMap<>());
        cache.get(value).put(blinkCount, count);

        return count;
    }
}
