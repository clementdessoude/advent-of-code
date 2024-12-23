package advent_of_code.year2023.day19;

import java.util.HashMap;
import java.util.Map;

class PartRange {

    private final Map<Category, Range> description;

    PartRange() {
        this.description = new HashMap<>();
        description.put(Category.X, new Range(1, 4000));
        description.put(Category.M, new Range(1, 4000));
        description.put(Category.A, new Range(1, 4000));
        description.put(Category.S, new Range(1, 4000));
    }

    public PartRange(Map<Category, Range> description) {
        this.description = new HashMap<>(description);
    }

    public Range get(Category category) {
        return description.get(category);
    }

    public PartRange with(Category category, Range range) {
        PartRange partRange = new PartRange(description);
        partRange.description.put(category, range);

        return partRange;
    }

    public Long possibilities() {
        return description
            .values()
            .stream()
            .mapToLong(i -> i.end() - i.start() + 1)
            .reduce(1, (a, b) -> a * b);
    }
}
