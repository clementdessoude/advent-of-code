
package adventOfCode.day21;

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
        String printed = map.stream().map(r -> String.join("", r)).collect(Collectors.joining("\n"));
        System.out.println(printed);
    }

    public Long part2(List<String> lines, int stepCount, int multiple) {
        return null;
    }

    private static Set<Location> fillInner2(
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
                .filter(l -> {
                    char content = map.get(l.i()).get(l.j()).charAt(0);

                    return content == '.';
                })
                .toList();

            for (var position: adjacent) {
                map.get(position.i()).set(position.j(), mark);
            }

            next.addAll(adjacent);
        }

        return next;
    }
}

