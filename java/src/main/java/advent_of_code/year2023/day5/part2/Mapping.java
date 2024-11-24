package advent_of_code.year2023.day5.part2;

import advent_of_code.year2023.day5.Range;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

record Mapping(Range range, long delta) {

    static List<Mapping> mappings(List<String> lines) {
        return lines
            .stream()
            .map(line -> Arrays
                .stream(line.split(" ")).mapToLong(Long::parseLong).toArray())
            .map(mapping -> new Mapping(new Range(mapping[1], mapping[1] + mapping[2] - 1), mapping[0] - mapping[1]))
            .sorted(Comparator.comparing(mapping -> mapping.range().start()))
            .toList();
    }
}
