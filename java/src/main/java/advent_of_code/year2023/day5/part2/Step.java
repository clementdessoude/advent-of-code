package advent_of_code.year2023.day5.part2;

import advent_of_code.year2023.day5.Range;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

record Step(
    String step,
    List<Range> elements,
    Mappings categoryMappings
) {

    Step next() {
        var categoryMapping = categoryMappings.get(step);

        return new Step(
            categoryMapping.description().destination(),
            nextElements(categoryMapping, elements),
            categoryMappings
        );
    }

    private List<Range> nextElements(
        CategoryMapping categoryMapping,
        List<Range> elements
    ) {
        return elements.stream()
                       .map(element -> nextElements(categoryMapping, element))
                       .flatMap(Collection::stream)
                       .toList();
    }

    private List<Range> nextElements(
        CategoryMapping categoryMapping,
        Range element
    ) {
        List<Mapping> overlappingMappings = categoryMapping
            .mappings()
            .stream()
            .filter(mapping -> mapping.range().overlaps(element))
            .toList();

        if (overlappingMappings.isEmpty()) {
            return List.of(element);
        }

        List<Range> result = new ArrayList<>();
        long currentElementPosition = element.start();
        for (Mapping currentMapping : overlappingMappings) {
            long currentMappingPosition = currentMapping.range().start();

            if (currentElementPosition < currentMappingPosition) {
                result.add(new Range(
                    currentElementPosition,
                    currentMappingPosition - 1
                ));
                currentElementPosition = currentMappingPosition;
            }
            if (element.end() <= currentMapping.range().end()) {
                result.add(new Range(
                    currentElementPosition
                        + currentMapping.delta(),
                    element.end() + currentMapping.delta()
                ));
                currentElementPosition = element.end() + 1;
                break;
            } else {
                result.add(new Range(
                    currentElementPosition
                        + currentMapping.delta(),
                    currentMapping.range().end()
                        + currentMapping.delta()
                ));
                currentElementPosition = currentMapping.range().end() + 1;
            }
        }

        if (currentElementPosition < element.end()) {
            result.add(new Range(currentElementPosition, element.end()));
        }

        return result;
    }

    boolean hasNotFinished() {
        return !step.equals("location");
    }

    long minimum() {
        return elements.stream().mapToLong(Range::start).min().orElseThrow();
    }
}
