package advent_of_code.year2024.day21;

import java.util.*;
import java.util.stream.Stream;

final class Solver {

    private final String code;
    private final Pad numericPad = NumericPad.getPad();
    private final Pad directionPad = DirectionPad.getPad();
    private final static Map<String, Collection<String>> cacheDirection = new HashMap<>();

    Solver(String code) {
        this.code = "A" + code;
    }

    int solve() {
        return solve(3);
    }

    int solve(int robotCount) {
        return minPath(robotCount) * numericPart();
    }

    int minPath(int robotCount) {
        var paths = paths(robotCount);
        return paths.mapToInt(String::length).min().orElseThrow();
    }

    Stream<String> paths(int robotCount) {
        return solve(code, robotCount);
    }

    Stream<String> shortestPathRobot1() {
        return solve(code, numericPad);
    }

    Stream<String> solve(String input, Pad pad) {
        if (input.length() == 1) {
            return Stream.of("");
        }

//        if (cacheDirection.containsKey(input)) {
//            return cacheDirection.get(input).parallelStream();
//        }

        var to = String.valueOf(input.charAt(1));
        var from = String.valueOf(input.charAt(0));
        var startPaths = pad.shortestMovesPart2(from, to);
        var otherPaths = solve(input.substring(1), pad);

        var result = otherPaths
            .flatMap(added -> startPaths.parallelStream().map(path -> path
                + "A"
                + added)).toList();

//        if (input.length() < 10) {
//            cacheDirection.put(input, result);
//        }

        return result.parallelStream();
    }

    Stream<String> shortestPathRobot2() {
        return shortestPathRobot1()
            .map(val -> "A"+ val)
            .flatMap(input -> solve(input, directionPad));
    }

    Stream<String> solve(String code, int robotCount) {
        System.out.println("Code: " + code);
        var stream = solve(code, numericPad);
        for (int i = 0; i < robotCount - 1; i++) {
            var tmp = stream
                .map(val -> "A"+ val)
                .flatMap(input -> solve(input, directionPad));
            var toto = tmp.toList();
            System.out.println("Robot: " + i);
            stream = toto.stream();
        }
        return stream;
    }

    int numericPart() {
        return Integer.parseInt(code.substring(1, code.length() - 1));
    }
}
