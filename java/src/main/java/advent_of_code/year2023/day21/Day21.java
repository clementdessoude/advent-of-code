
package advent_of_code.year2023.day21;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    public Long part1(List<String> lines, int stepCount) {
        List<List<String>> map = lines.stream().map(s -> s.chars().mapToObj(
            i1 -> String.valueOf((char) i1)).collect(Collectors.toList())).toList();

        Location sourceLocation = getSourceLocation(map);
        Set<Location> from = Set.of(sourceLocation);

        for (int i = 1; i <= stepCount; i++) {
            from = fillInner(map, from, i);
        }

//        print(map);

        return map.stream().mapToLong(
            row -> row.stream().mapToLong(s -> s.equals("O") ? 1 : 0).sum()
        ).sum() + 1;
    }

    private static Set<Location> fillInner(
        List<List<String>> map,
        Set<Location> from,
        int step
    ) {
        Set<Location> next = new HashSet<>();
        String mark = step % 2 == 0 ? "O" : "1";
        for (var location: from){
            List<Location> adjacent = location
                .adjacent(map)
                .stream()
                .filter(l -> map.get(l.i()).get(l.j()).charAt(0) == '.')
                .toList();

            for (var position: adjacent) {
                map.get(position.i()).set(position.j(), mark);
            }

            next.addAll(adjacent);
        }

        return next;
    }

    private static Location getSourceLocation(List<List<String>> map) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (map.get(i).get(j).equals("S")) {
                    return new Location(i, j);
                }
            }
        }

        throw new RuntimeException();
    }

    static void print(List<List<String>> map) {
        String printed = map.stream().map(r -> String.join(",", r)).collect(Collectors.joining("\n"));
        System.out.println(printed);
    }

    public Long part2(List<String> lines, int stepCount) {
        List<List<String>> map = lines
            .stream()
            .map(s -> s.chars().mapToObj(i1 -> String.valueOf((char) i1))
            .collect(Collectors.toList())).toList();

        Location tmp = getSourceLocation(map);
        Location sourceLocation = new Location(2 * map.size() + tmp.i(), 2 * map.size() + tmp.j());

        var newMap = multiple(map, 5);

        Set<Location> from = Set.of(sourceLocation);
        for (int i = 1; i <= 10000; i++) {
            from = fillInner2(newMap, from, i);
        }

        long count = 0;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (a == 1 && b == 1) {
                    continue;
                }

                for (int i = 0; i < map.size(); i++) {
                    for (int j = 0; j < map.size(); j++) {
                        if (map.get(i).get(j).equals("#")) {
                            continue;
                        }

                        String start = newMap.get(map.size() + i).get(map.size() + j);
                        long v0 = start.equals("S") ? 0 : Long.parseLong(start);

                        String current = newMap
                            .get(a * map.size() + i)
                            .get(b * map.size() + j);

                        long v1 = 0;
                        v1 = Long.parseLong(current);

                        if ((stepCount + v0) % 2 == 1 && v1 % 2 == 0) {
                            continue;
                        }

                        if (v1 == v0) {
                            throw new RuntimeException();
                        }
                        long k = (stepCount - v0) / (v1 - v0);
                        if (v1 % 2 == 1) {
                            count += k / 2 + (stepCount + v0) % 2;
                        } else {
                            count += k;
                        }
                    }
                }
            }
        }

//        print(newMap);

        return count;
    }

    public List<List<String>> multiple(List<List<String>> map, int k) {
        List<List<String>> newMap = new ArrayList<>(k * map.size());
        for (int i = 0; i < k; i++) {
            for (var row: map) {
                List<String> newRow = new ArrayList<>(k * map.size());
                for (int j = 0; j < k; j++) {
                    if (i == (k - 1) / 2 && j == i) {
                        newRow.addAll(row);
                    } else {
                        newRow.addAll(row.stream().map(s -> s.equals("S") ? "." : s).toList());
                    }
                }
                newMap.add(newRow);
            }
        }

        return newMap;
    }

    private static Set<Location> fillInner2(
        List<List<String>> map,
        Set<Location> from,
        int step
    ) {
        Set<Location> next = new HashSet<>();
        for (var location: from){
            List<Location> adjacent = location
                .adjacent(map)
                .stream()
                .filter(l ->  map.get(l.i()).get(l.j()).charAt(0) == '.')
                .toList();

            for (var position: adjacent) {
                map.get(position.i()).set(position.j(), step + "");
            }

            next.addAll(adjacent);
        }

        return next;
    }
}

