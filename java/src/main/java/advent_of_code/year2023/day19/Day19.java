
package advent_of_code.year2023.day19;

import java.util.*;

public class Day19 {

    public Long part1(List<String> lines) {
        Parse result = parse(lines);

        return result.parts()
                     .stream()
                     .filter(p -> filter(p, result.workflows()))
                     .mapToLong(part ->
                           Arrays.stream(Category.values()).mapToLong(part::get).sum()
            ).sum();
    }

    private static Parse parse(List<String> lines) {
        Map<String, Workflow> workflows = new HashMap<>();
        List<Part> parts = new ArrayList<>();

        boolean isParts = false;
        for (var row: lines) {
            if (row.isBlank()) {
                isParts = true;
                continue;
            }

            if (isParts) {
                parts.add(new Part(row));
            } else {
                String[] split = row.split("\\{");
                var workflowName = split[0];
                workflows.put(workflowName, new Workflow(row));
            }
        }
        Parse result = new Parse(workflows, parts);
        return result;
    }

    private record Parse(Map<String, Workflow> workflows, List<Part> parts) {
    }

    private boolean filter(Part part, Map<String, Workflow> workflows) {
        String currentWorkflow = "in";
        while (!currentWorkflow.equals("A") && !currentWorkflow.equals("R")) {
            Workflow workflow = workflows.get(currentWorkflow);
            currentWorkflow = workflow.next(part);
        }
        return currentWorkflow.equals("A");
    }

    public Long part2(List<String> lines) {
        Map<String, Workflow> workflows = parse(lines).workflows();

        String currentWorkflow = "in";
        PartRange partRange = new PartRange();
        List<PartRange> partRanges = extracted(
            workflows,
            currentWorkflow,
            partRange
        );

        return partRanges
            .stream()
            .mapToLong(PartRange::possibilities)
            .sum();
    }

    private static List<PartRange> extracted(
        Map<String, Workflow> workflows,
        String currentWorkflow,
        PartRange currentRange
    ) {
        List<PartRange> partRanges = new ArrayList<>();

        Workflow workflow = workflows.get(currentWorkflow);
        Map<String, List<PartRange>> updated = workflow.next(currentRange);

        if (updated.containsKey("A")) {
            partRanges.addAll(updated.get("A"));
        }

        updated
            .entrySet()
            .stream()
            .filter(entry -> !entry.getKey().equals("A") && !entry.getKey().equals("R"))
            .map(entry -> {
                if (entry.getValue().size() > 1) {
                    throw new RuntimeException();
                }

                return extracted(workflows, entry.getKey(), entry.getValue().get(0));
            })
            .flatMap(Collection::stream)
            .forEach(partRanges::add);

        return partRanges;
    }
}

