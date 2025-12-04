package advent_of_code.year2025.day4;

import advent_of_code.utils.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day4 {

    Long part1(List<String> lines) {
        var grid = parse(lines);

        var maxY = lines.size();
        var maxX = lines.getFirst().length();

        var result = 0L;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                var location = new Location(x, y);
                if (grid.get(location) != '@') {
                    continue;
                }

                var numberOfRolls = location
                    .adjacentsInGrid(0, 0, maxX, maxY)
                    .stream()
                    .filter(loc -> grid.get(loc) == '@')
                    .count();
                if (numberOfRolls < 4) {
                    result += 1;
                }
            }
        }

        return result;
    }

    private Map<Location, Character> parse(List<String> lines) {
        Map<Location, Character> grid = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                grid.put(new Location(j, i), lines.get(i).charAt(j));
            }
        }

        return grid;
    }

    Long part2(List<String> lines) {
        var grid = parse(lines);

        var maxY = lines.size();
        var maxX = lines.getFirst().length();

        var result = 0L;

        while (true) {
            var step = 0;
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    var location = new Location(x, y);
                    if (grid.get(location) != '@') {
                        continue;
                    }

                    var numberOfRolls = location
                        .adjacentsInGrid(0, 0, maxX, maxY)
                        .stream()
                        .filter(loc -> grid.get(loc) == '@')
                        .count();
                    if (numberOfRolls < 4) {
                        step += 1;
                        grid.put(new Location(x, y), '.');
                    }
                }
            }

            if (step == 0) {
                break;
            }
            result += step;
        }

        return result;
    }
}
