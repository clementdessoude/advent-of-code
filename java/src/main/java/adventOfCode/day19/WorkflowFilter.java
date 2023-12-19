package adventOfCode.day19;

import java.util.function.Function;

class WorkflowFilter {

    private final Function<Part, Boolean> check;
    private final String destination;

    WorkflowFilter(
            Category category,
            Character operation,
            Integer value,
            String destination
    ) {
        this.destination = destination;
        this.check = matchFunction(category, operation, value);
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

    public String destination() {
        return this.destination;
    }
}
