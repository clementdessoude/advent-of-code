
package adventOfCode.day19;

import java.util.*;

public class Day19 {

    public Long part1(List<String> lines) {
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

        return parts
            .stream()
            .filter(p -> filter(p, workflows))
            .mapToLong(part ->
                           Arrays.stream(Category.values()).mapToLong(part::get).sum()
            ).sum();
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
        return null;
    }
}

