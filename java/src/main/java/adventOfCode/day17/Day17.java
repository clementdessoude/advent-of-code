
package adventOfCode.day17;

import java.util.*;
import java.util.stream.IntStream;

public class Day17 {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public Long part1(List<String> lines) {
        List<List<Position>> positions = IntStream
            .range(0, lines.size())
            .mapToObj(i -> IntStream
                .range(0, lines.get(0).length())
                .mapToObj(j -> new Position(
                    i,
                    j,
                    Integer.parseInt(String.valueOf(lines.get(i).charAt(j))))
                )
                .toList()
            )
            .toList();

        SolverPart1 solverPart1 = new SolverPart1(positions);
        solverPart1.calculateShortestPathFromSource();

        var destination = positions.getLast().getLast();
//        var destination = new Location(0, 5);

        LocationWithDirection locationWithDirection = solverPart1.heatLosses
            .get(new Location(destination.i(), destination.j()))
            .values()
            .stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .min(Comparator.comparing(LocationWithDirection::heatLoss))
            .orElseThrow();

        displayPath(locationWithDirection.path(), lines);

        return locationWithDirection.heatLoss();
    }

    void displayPath(List<Location> path, List<String> lines) {
        List<List<String>> map = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var rowList = new ArrayList<String>();
            for (int j = 0; j < lines.get(0).length(); j++) {
                rowList.add(ANSI_RED + lines.get(i).charAt(j) + ANSI_RESET);
            }
            map.add(rowList);
        }

        for (int i = 1; i < path.size(); i++) {
            var to = path.get(i);
            var from = path.get(i - 1);
            var direction = Direction.direction(from, to);
            String c = switch (direction) {
                case LEFT -> ANSI_GREEN + "<" + ANSI_RESET;
                case UP -> ANSI_GREEN + "^" + ANSI_RESET;
                case RIGHT -> ANSI_GREEN + ">" + ANSI_RESET;
                case DOWN -> ANSI_GREEN + "v" + ANSI_RESET;
            };

            map.get(to.i()).set(to.j(), c);
        }

        System.out.println(
            String.join("\n", map.stream().map(r -> String.join("", r)).toList())
        );

    }

    public Long part2(List<String> lines) {
        List<List<Position>> positions = IntStream
            .range(0, lines.size())
            .mapToObj(i -> IntStream
                .range(0, lines.get(0).length())
                .mapToObj(j -> new Position(
                    i,
                    j,
                    Integer.parseInt(String.valueOf(lines.get(i).charAt(j))))
                )
                .toList()
            )
            .toList();

        SolverPart2 solverPart2 = new SolverPart2(positions);
        solverPart2.calculateShortestPathFromSource();

        var destination = positions.getLast().getLast();

        LocationWithDirection locationWithDirection = solverPart2.heatLosses
            .get(new Location(destination.i(), destination.j()))
            .values()
            .stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .filter(l -> l.count() >= 4)
            .min(Comparator.comparing(LocationWithDirection::heatLoss))
            .orElseThrow();

        displayPath(locationWithDirection.path(), lines);

        return locationWithDirection.heatLoss();
    }
}

