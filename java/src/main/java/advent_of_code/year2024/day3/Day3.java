package advent_of_code.year2024.day3;

import static java.lang.Integer.parseInt;

import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class Day3 {

    private static final Pattern OPERATION_MATCHER = Pattern.compile(
        "mul\\((?<first>\\d+),(?<second>\\d+)\\)"
    );

    Long part1(List<String> lines) {
        return lines.stream().mapToLong(this::process).sum();
    }

    private Long process(String line) {
        var factors = parse(line);

        return factors.stream().mapToLong(pair -> pair.first() * pair.second()).sum();
    }

    private static List<Pair<Long, Long>> parse(String line) {
        var operationMatcher = OPERATION_MATCHER.matcher(line);
        List<Pair<Long, Long>> factors = new ArrayList<>();
        while (operationMatcher.find()) {
            var first = Long.parseLong(operationMatcher.group("first"));
            var second = Long.parseLong(operationMatcher.group("second"));
            factors.add(new Pair<>(first, second));
        }

        return factors;
    }

    Long part2(List<String> lines) {
        return null;
    }
}
