package advent_of_code.year2024.day18;

import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.List;

class Day18 {

    int part1(
        List<String> lines,
        Pair<Integer, Integer> dimensions,
        int nanoseconds
    ) {
        var space = MemorySpace.atNano(parse(lines), dimensions, nanoseconds);
        return space.shortestDistanceAfter(nanoseconds);
    }

    static List<Location> parse(List<String> lines) {
        return lines.stream().map(row -> {
            var split = row.split(",");
            return new Location(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }).toList();
    }

    String part2(
        List<String> lines,
        Pair<Integer, Integer> dimensions,
        int nanoseconds
    ) {
        var space = new MemorySpace(parse(lines), dimensions, nanoseconds);
        var firstBlockedBy = space.firstBlockedBy(nanoseconds);
        return firstBlockedBy.x() + "," + firstBlockedBy.y();
    }
}
