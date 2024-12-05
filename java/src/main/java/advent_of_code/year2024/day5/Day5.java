package advent_of_code.year2024.day5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day5 {

    Long part1(List<String> lines) {
        var orderingRules = new PageOrderingRules(lines);
        var updates = IntStream.range(lines.indexOf("") + 1, lines.size())
            .mapToObj(i -> lines.get(i).split(","))
            .map(split -> Arrays.stream(split).map(Integer::parseInt).toList());

        return updates.filter(orderingRules::isInCorrectOrder).mapToLong(Day5::getMiddlePage).sum();
    }

    private static long getMiddlePage(List<Integer> integers) {
        if (integers.size() % 2 == 0) {
            throw new IllegalArgumentException();
        }
        var middlePage = (integers.size() - 1) / 2;

        return integers.get(middlePage);
    }

    Long part2(List<String> lines) {
        var orderingRules = new PageOrderingRules(lines);
        var updates = IntStream.range(lines.indexOf("") + 1, lines.size())
            .mapToObj(i -> lines.get(i).split(","))
            .map(split -> Arrays.stream(split).map(Integer::parseInt).toList());

        return updates
            .filter(orderingRules::isNotInCorrectOrder)
            .map(orderingRules::reorder)
            .mapToLong(Day5::getMiddlePage)
            .sum();
    }
}
