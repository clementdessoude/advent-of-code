package advent_of_code.year2024.day14;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.MULTILINE;

import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day14 {

    private static final Pattern ROBOT_PATTERN = Pattern.compile(
        "^p=(?<x>\\d+),(?<y>\\d+) v=(?<vx>-?\\d+),(?<vy>-?\\d+)$"
    );

    Integer part1(List<String> lines, Pair<Integer, Integer> roomSize) {
        var robots = lines.stream().map(row -> parse(row, roomSize)).map(r -> r.move(100)).toList();

        var locations = robots
            .stream()
            .collect(Collectors.toMap(Robot::location, v -> 1, Integer::sum));

        //        print(locations, roomSize);

        var byQuadrant = robots
            .stream()
            .map(Robot::quadrant)
            .filter(q -> q != 0)
            .collect(Collectors.toMap(Function.identity(), e -> 1, Integer::sum));
        return byQuadrant.values().stream().reduce(1, (a, b) -> a * b);
    }

    private static Robot parse(String row, Pair<Integer, Integer> roomSize) {
        Matcher matcher = ROBOT_PATTERN.matcher(row);
        var __ = matcher.matches();

        return new Robot(
            Integer.parseInt(matcher.group("x")),
            Integer.parseInt(matcher.group("y")),
            Integer.parseInt(matcher.group("vx")),
            Integer.parseInt(matcher.group("vy")),
            roomSize
        );
    }

    int part2(List<String> lines) {
        int result = 6446;

        //        Pattern FOUR_CONSECUTIVE_DIGITS = Pattern.compile("(.*)\\d\\d\\d\\d\\d(.*)", DOTALL);
        //        var roomSize = new Pair<>(101, 103);
        //        var robots = lines.stream().map(row -> parse(row, roomSize)).toList();
        //        for (int i = 1; i < 10_000; i++) {
        //            robots.forEach(Robot::move);
        //            var locations = robots
        //                .stream()
        //                .collect(Collectors.toMap(Robot::location, v -> 1, Integer::sum));
        //            var map = print(locations, roomSize);
        //            var description = String.join("\n", map.stream().map(r -> String.join("", r)).toList());
        //
        //            var hasMatch = FOUR_CONSECUTIVE_DIGITS.asMatchPredicate().test(description);
        //            if (hasMatch) {
        //                System.out.println(i);
        //                Display.display(map);
        //            }
        //        }

        return result;
    }

    private List<List<String>> print(
        Map<Location, Integer> locations,
        Pair<Integer, Integer> roomSize
    ) {
        List<List<String>> map = new ArrayList<>(roomSize.second());
        for (int i = 0; i < roomSize.second(); i++) {
            map.add(new ArrayList<>(roomSize.first()));
            for (int j = 0; j < roomSize.first(); j++) {
                map.get(i).add(".");
            }
        }

        locations.forEach((location, count) ->
            map.get(location.y()).set(location.x(), String.valueOf(count))
        );

        return map;
    }
}
