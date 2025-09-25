package advent_of_code.year2020.day11;

import advent_of_code.utils.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day11 {

    Long part1(List<String> lines) {
        var zone = parse(lines);
        while (!zone.isStable()) {
            zone = zone.run(false);
        }
        return zone.occupiedCount();
    }

    private Zone parse(List<String> lines) {
        Map<Location, Integer> grid = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            var line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                var location = new Location(x, y);
                var value =
                    switch (line.charAt(x)) {
                        case '.' -> Zone.FLOOR;
                        case 'L' -> Zone.SEAT;
                        default -> throw new IllegalArgumentException(
                            "Invalid character: " + line.charAt(x)
                        );
                    };
                grid.put(location, value);
            }
        }

        return new Zone(grid, lines.getFirst().length(), lines.size());
    }

    Long part2(List<String> lines) {
        var zone = parse(lines);
        while (!zone.isStable()) {
            zone = zone.run(true);
        }
        return zone.occupiedCount();
    }
}
