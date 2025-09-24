package advent_of_code.year2024.day21;

import advent_of_code.utils.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

final class Solver {

    private final String code;
    private final Pad numericPad = NumericPad.getPad();
    private final Pad directionPad = DirectionPad.getPad();

    Solver(String code) {
        this.code = code;
    }

    long complexity(int robotCount, Map<Pair<String, String>, Map<Integer, Long>> cache) {
        return minPath(robotCount, cache) * numericPart();
    }

    Stream<String> shortestPath(String input, Pad pad) {
        if (input.length() == 1) {
            return Stream.of("");
        }

        var to = String.valueOf(input.charAt(1));
        var from = String.valueOf(input.charAt(0));
        var startPaths = pad.shortestMoves(from, to);
        var otherPaths = shortestPath(input.substring(1), pad);

        var result = otherPaths
            .flatMap(added -> startPaths.parallelStream().map(path -> path + "A" + added))
            .toList();

        return result.stream();
    }

    int numericPart() {
        return Integer.parseInt(code.substring(0, code.length() - 1));
    }

    long minPath(int robotCount, Map<Pair<String, String>, Map<Integer, Long>> cache) {
        return shortestPath("A" + code, numericPad)
            .mapToLong(path -> buttonPressCount(path, robotCount, cache))
            .min()
            .orElseThrow();
    }

    private long buttonPressCount(
        String input,
        int robotCount,
        Map<Pair<String, String>, Map<Integer, Long>> cache
    ) {
        if (robotCount == 1) {
            return input.length();
        }
        var path = "A" + input;
        var count = 0L;
        for (int i = 1; i < path.length(); i++) {
            var pair = new Pair<>(
                String.valueOf(path.charAt(i - 1)),
                String.valueOf(path.charAt(i))
            );
            var toAdd = buttonPressForPair(pair, cache, robotCount);
            count += toAdd;
        }
        return count;
    }

    private long buttonPressForPair(
        Pair<String, String> pair,
        Map<Pair<String, String>, Map<Integer, Long>> cache,
        int robotCount
    ) {
        Map<Integer, Long> buttonPressForPairCache = cache.computeIfAbsent(pair, k ->
            new HashMap<>()
        );

        if (buttonPressForPairCache.containsKey(robotCount)) {
            return buttonPressForPairCache.get(robotCount);
        }

        if (pair.first().equals(pair.second())) {
            buttonPressForPairCache.put(robotCount, 1L);
            return 1L;
        }

        Collection<String> moves = directionPad.shortestMoves(pair.first(), pair.second());

        var min = moves
            .stream()
            .mapToLong(move -> buttonPressCount(move + "A", robotCount - 1, cache))
            .min()
            .orElseThrow();

        buttonPressForPairCache.put(robotCount, min);
        return min;
    }
}
