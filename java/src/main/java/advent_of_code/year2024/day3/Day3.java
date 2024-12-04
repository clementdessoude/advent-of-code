package advent_of_code.year2024.day3;

import static java.lang.Integer.parseInt;

import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class Day3 {

    private static final Pattern OPERATION_MATCHER = Pattern.compile(
        "(mul\\((?<first>\\d+),(?<second>\\d+)\\))|(?<do>(do\\(\\)))|(?<donot>(don't\\(\\)))"
    );

    Long part1(List<String> lines) {
        return process(String.join("", lines), false);
    }

    private Long process(String line, boolean isPart2) {
        var factors = parse(line, isPart2);

        return factors.stream().mapToLong(pair -> pair.first() * pair.second()).sum();
    }

    private static List<Pair<Long, Long>> parse(String line, boolean isPart2) {
        var operationMatcher = OPERATION_MATCHER.matcher(line);
        List<Pair<Long, Long>> factors = new ArrayList<>();
        boolean isActivated = true;
        while (operationMatcher.find()) {
            if (operationMatcher.group("do") != null) {
                isActivated = true;
            } else if (operationMatcher.group("donot") != null) {
                isActivated = false;
            } else {
                if (isActivated || !isPart2) {
                    var first = Long.parseLong(operationMatcher.group("first"));
                    var second = Long.parseLong(operationMatcher.group("second"));
                    factors.add(new Pair<>(first, second));
                }
            }
        }

        return factors;
    }

    Long part2(List<String> lines) {
        return process(String.join("", lines), true);
    }
}
