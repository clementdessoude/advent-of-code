package advent_of_code.year2022.day8;

import advent_of_code.utils.Location;
import java.util.Collection;
import java.util.List;

class Day8 {

    int part1(List<String> lines) {
        var forest = forest(lines);

        Collection<Location> locations = forest.visibleTrees();
        return locations.size();
    }

    private Forest forest(List<String> lines) {
        return new Forest(
            lines
                .stream()
                .map(row ->
                    row
                        .chars()
                        .mapToObj(c -> (char) c)
                        .map(String::valueOf)
                        .map(Integer::parseInt)
                        .toList()
                )
                .toList()
        );
    }

    Long part2(List<String> lines) {
        return null;
    }
}
