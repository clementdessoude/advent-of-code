package advent_of_code.year2023.day5.part1;

import java.util.*;

public final class Part1Solver {

    private final List<Long> seeds;
    private final Map<String, CategoryMapping> mappings;

    public Part1Solver(List<String> lines) {
        this.seeds = parseSeeds(lines);
        this.mappings = getMappings(lines);
    }

    public Long solve() {
        return seeds.stream().mapToLong(start -> location(start, mappings)).min().orElseThrow();
    }

    private static List<Long> parseSeeds(List<String> lines) {
        return Arrays.stream(lines.get(0).split(":")[1].split(" "))
            .filter(s -> !s.isBlank())
            .map(Long::parseLong)
            .toList();
    }

    private static Map<String, CategoryMapping> getMappings(List<String> lines) {
        Map<String, CategoryMapping> categoryMappings = new HashMap<>();
        CategoryMapping current = null;
        for (int i = 2; i < lines.size(); i++) {
            String row = lines.get(i);
            if (row.contains("map")) {
                String[] split = row.split("-");
                String source = split[0];
                String destination = split[2].split(" ")[0];
                current = new CategoryMapping(destination);
                categoryMappings.put(source, current);
            } else if (row.isEmpty()) {
                continue;
            } else {
                String[] split = row.split(" ");
                long source = Long.parseLong(split[1]);
                long destination = Long.parseLong(split[0]);
                long range = Long.parseLong(split[2]);
                current.addMapping(new Mapping(source, destination, range));
            }
        }

        return categoryMappings;
    }

    private Long location(long start, Map<String, CategoryMapping> mappings) {
        String category = "seed";
        long location = start;
        while (!category.equals("location")) {
            final long currentLocation = location;
            CategoryMapping categoryMapping = mappings.get(category);
            Optional<Mapping> matchingMapping = categoryMapping
                .mappings()
                .stream()
                .filter(
                    mapping ->
                        currentLocation >= mapping.source() &&
                        currentLocation < mapping.source() + mapping.range()
                )
                .findFirst();

            if (matchingMapping.isPresent()) {
                var mapping = matchingMapping.get();
                location = mapping.destination() + (location - mapping.source());
            }
            category = categoryMapping.destination();
        }

        return location;
    }
}
