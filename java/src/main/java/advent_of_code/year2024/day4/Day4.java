package advent_of_code.year2024.day4;

import advent_of_code.utils.Location;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Day4 {

    int part1(List<String> lines) {
        List<Location> xs = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == 'X') {
                    xs.add(new Location(x, y));
                }
            }
        }

        List<List<Location>> locations = new ArrayList<>();
        for (Location loc : xs) {
            var adjacents = loc.adjacentsInGrid(0, 0, lines.getFirst().length(), lines.size());
            for (Location adjacent : adjacents) {
                if (charAt(adjacent, lines) == 'M') {
                    var xDistance = adjacent.x() - loc.x();
                    var yDistance = adjacent.y() - loc.y();
                    var potentialA = new Location(
                        adjacent.x() + xDistance,
                        adjacent.y() + yDistance
                    );

                    var potentialS = new Location(
                        adjacent.x() + 2 * xDistance,
                        adjacent.y() + 2 * yDistance
                    );

                    if (
                        isInGrid(potentialA, lines) &&
                        charAt(potentialA, lines) == 'A' &&
                        isInGrid(potentialS, lines) &&
                        charAt(potentialS, lines) == 'S'
                    ) {
                        locations.add(List.of(loc, adjacent, potentialA, potentialS));
                    }
                }
            }
        }

        //        display(locations, lines);
        return locations.size();
    }

    private char charAt(Location loc, List<String> lines) {
        return lines.get(loc.y()).charAt(loc.x());
    }

    private boolean isInGrid(Location loc, List<String> lines) {
        return (
            loc.x() >= 0 &&
            loc.x() < lines.getFirst().length() &&
            loc.y() >= 0 &&
            loc.y() < lines.size()
        );
    }

    void display(List<List<Location>> locations, List<String> lines) {
        List<List<String>> grid = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < lines.getFirst().length(); j++) {
                row.add(".");
            }
            grid.add(row);
        }

        locations
            .stream()
            .flatMap(Collection::stream)
            .forEach(loc -> grid.get(loc.y()).set(loc.x(), String.valueOf(charAt(loc, lines))));

        for (int i = 0; i < grid.size(); i++) {
            System.out.println(String.join("", grid.get(i)));
        }
    }

    int part2(List<String> lines) {
        List<Location> as = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == 'A') {
                    as.add(new Location(x, y));
                }
            }
        }

        List<List<Location>> locations = new ArrayList<>();
        for (Location loc : as) {
            var adjacents = loc
                .adjacentsInGrid(0, 0, lines.getFirst().length(), lines.size())
                .stream()
                .filter(other -> !loc.isOnSameRow(other) && !loc.isOnSameColumn(other))
                .toList();

            var ms = adjacents
                .stream()
                .filter(potential -> charAt(potential, lines) == 'M')
                .toList();
            var ss = adjacents
                .stream()
                .filter(potential -> charAt(potential, lines) == 'S')
                .toList();

            if (
                (ms.size() == 2 && ss.size() == 2) &&
                (ms.getFirst().isOnSameColumn(ms.getLast()) ||
                    ms.getFirst().isOnSameRow(ms.getLast()))
            ) {
                locations.add(
                    List.of(loc, ms.getFirst(), ms.getLast(), ss.getFirst(), ss.getLast())
                );
            }
        }

        //        display(locations, lines);
        return locations.size();
    }
}
