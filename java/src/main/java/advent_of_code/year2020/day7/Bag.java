package advent_of_code.year2020.day7;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Bag {

    private final String color;
    private final Map<Bag, Integer> counts = new HashMap<>();
    private final Map<Bag, Boolean> contains = new HashMap<>();
    private Long bagSize;

    Bag(String color) {
        this.color = color;
    }

    public Bag add(Bag bag, int count) {
        counts.put(bag, count);
        return this;
    }

    public boolean contains(Bag bag) {
        if (contains.containsKey(bag)) {
            return contains.get(bag);
        }

        for (Bag b : counts.keySet()) {
            if (b.contains(bag) || b.color.equals(bag.color)) {
                contains.put(bag, true);
                return true;
            }
        }

        contains.put(bag, false);
        return false;
    }

    public long bagSize() {
        if (bagSize != null) {
            return bagSize;
        }

        this.bagSize = counts
            .entrySet()
            .stream()
            .mapToLong(entry -> {
                var bag = entry.getKey();
                var count = entry.getValue();

                return count * (bag.bagSize() + 1);
            })
            .sum();
        return this.bagSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return Objects.equals(color, bag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color);
    }
}
