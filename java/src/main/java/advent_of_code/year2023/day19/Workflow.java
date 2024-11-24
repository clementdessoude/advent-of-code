package advent_of_code.year2023.day19;

import java.util.*;

class Workflow {
    private final List<WorkflowFilter> filters;

    public Workflow(String row) {
        var rawFilters = row.split("\\{")[1].replace("}", "").split(",");

        this.filters = Arrays.stream(rawFilters).map(raw -> {
            if (!raw.contains(":")) {
                return new WorkflowFilter(null, null, null, raw);
            }


            Category category = switch (raw.charAt(0)) {
                case 'x' -> Category.X;
                case 'm' -> Category.M;
                case 'a' -> Category.A;
                case 's' -> Category.S;
                default -> throw new IllegalStateException("Unexpected value: " + raw.charAt(0));
            };
            char operation = raw.charAt(1);
            var split = raw.split(":");
            int value = Integer.parseInt(split[0].split(String.valueOf(operation))[1]);
            String destination = split[1];

            return new WorkflowFilter(category, operation, value, destination);
        }).toList();
    }

    public String next(Part part) {
        for (var filter: filters) {
            if (filter.match(part)) {
                return filter.destination();
            }
        }
        throw new RuntimeException();
    }

    public Map<String, List<PartRange>> next(PartRange partRange) {
        Map<String, List<PartRange>> result = new HashMap<>();

        var current = partRange;
        for (var filter: filters) {
            Split<PartRange> split = filter.split(current);

            List<PartRange> orDefault = result.getOrDefault(filter.destination(), new ArrayList<>());
            orDefault.add(split.matched());
            result.put(filter.destination(), orDefault);

            current = split.notMatched();
        }

        return result;
    }
}
