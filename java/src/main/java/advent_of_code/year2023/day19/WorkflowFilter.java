package advent_of_code.year2023.day19;

import java.util.function.Function;

class WorkflowFilter {

    private final Function<Part, Boolean> check;
    private final String destination;
    private Function<PartRange, Split<PartRange>> splitFunction;

    WorkflowFilter(
            Category category,
            Character operation,
            Integer value,
            String destination
    ) {
        this.destination = destination;
        this.check = matchFunction(category, operation, value);
        this.splitFunction = splitFunction(category, operation, value);
    }

    boolean match(Part part) {
        return check.apply(part);
    }

    private Function<Part, Boolean> matchFunction(Category category, Character operation, Integer value) {
        return part -> switch (operation) {
            case null -> true;
            case '>' -> part.get(category) > value;
            case '<' -> part.get(category) < value;
            default -> throw new RuntimeException();
        };
    }

    private Function<PartRange, Split<PartRange>> splitFunction(Category category, Character operation, Integer value) {
        return partRange -> switch (operation) {
            case null -> new Split<>(partRange, null);
            case '>' -> above(partRange, category, value);
            case '<' -> below(partRange, category, value);
            default -> throw new RuntimeException();
        };
    }

    private Split<PartRange> above(PartRange partRange, Category category, Integer value) {
        Range range = partRange.get(category);
        Range above = range.above(value);
        Range below = range.below(value + 1);


        var matched = above != null ? partRange.with(category, above) : null;
        var notMatched = below != null ? partRange.with(category, below) : null;

        return new Split<>(matched, notMatched);
    }

    private Split<PartRange> below(PartRange partRange, Category category, Integer value) {
        Range range = partRange.get(category);
        Range above = range.above(value - 1);
        Range below = range.below(value);


        var matched = below != null ? partRange.with(category, below) : null;
        var notMatched = above != null ? partRange.with(category, above) : null;

        return new Split<>(matched, notMatched);
    }

    public String destination() {
        return this.destination;
    }

    public Split<PartRange> split(PartRange partRange) {
        return splitFunction.apply(partRange);
    }
}
