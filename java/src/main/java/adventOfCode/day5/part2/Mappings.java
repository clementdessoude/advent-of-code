package adventOfCode.day5.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class Mappings {
    private final Map<String, CategoryMapping> value;

    Mappings(List<String> lines) {
        var linesGroupedByCategory = groupByCategory(lines);

        this.value = linesGroupedByCategory
            .stream()
            .map(CategoryMapping::from)
            .collect(Collectors.toMap(
                categoryMapping -> categoryMapping.description().source(),
                categoryMapping -> categoryMapping
            ));
    }

    CategoryMapping get(String key) {
        return value.get(key);
    }

    private static List<List<String>> groupByCategory(List<String> lines) {
        List<List<String>> result = new ArrayList<>();

        List<String> current = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            String row = lines.get(i);
            if (row.isEmpty()) {
                result.add(current);
                current = new ArrayList<>();
            } else {
                current.add(row);
            }
        }
        result.add(current);

        return result;
    }
}
