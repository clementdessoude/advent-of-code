package advent_of_code.year2024.day21;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

final class Solver {

    private final String code;
    private final Pad numericPad = NumericPad.getPad();
    private final Pad directionPad = DirectionPad.getPad();

    Solver(String code) {
        this.code = "A" + code;
    }

    int solve() {
        return minPath() * numericPart();
    }

    int minPath() {
        return paths().stream().min(Comparator.comparing(String::length)).map(String::length).orElseThrow();
    }

    Collection<String> paths() {
        return shortestPathRobot2()
            .stream()
            .map(val -> "A"+ val)
            .flatMap(input -> solve(input, directionPad).stream())
            .toList();
    }

    Collection<String> shortestPathRobot1() {
        return solve(code, numericPad);
    }

    Collection<String> solve(String input, Pad pad) {
        Collection<String> currentPaths = new ArrayList<>();
        currentPaths.add("");
        for (int i = 1; i < input.length(); i++) {
            var to = String.valueOf(input.charAt(i));
            var from = String.valueOf(input.charAt(i - 1));
            var shortestPaths = pad.shortestMoves(from, to);
            currentPaths = currentPaths
                .stream()
                .flatMap(path -> shortestPaths.stream().map(added -> path + added + "A"))
                .toList();
        }
        return currentPaths;
    }

    Collection<String> shortestPathRobot2() {
        return shortestPathRobot1()
            .stream()
            .map(val -> "A"+ val)
            .flatMap(input -> solve(input, directionPad).stream())
            .toList();
    }

    int numericPart() {
        return Integer.parseInt(code.substring(1, code.length() - 1));
    }
}
