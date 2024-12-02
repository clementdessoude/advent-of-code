package advent_of_code.year2024.day1;

import advent_of_code.utils.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day1 {

    int part1(List<String> lines) {
        var input = parse(lines);

        int result = 0;
        for (int i = 0; i < input.first().size(); i++) {
            result += Math.abs(input.first().get(i) - input.second().get(i));
        }

        return result;
    }

    int part2(List<String> lines) {
        var input = parse(lines);

        Map<Integer, Integer> numbersCount = new HashMap<>();
        for (int i = 0; i < input.second().size(); i++) {
            numbersCount.compute(input.second().get(i), (k, v) -> v == null ? 1 : v + 1);
        }

        return input
            .first()
            .stream()
            .mapToInt(number -> number * numbersCount.getOrDefault(number, 0))
            .sum();
    }

    private Pair<List<Integer>, List<Integer>> parse(List<String> lines) {
        var input = lines
            .stream()
            .map(s -> s.split(" "))
            .map(split ->
                new Pair<>(Integer.parseInt(split[0]), Integer.parseInt(split[split.length - 1]))
            )
            .toList();

        var firstList = input.stream().map(Pair::first).sorted().toList();
        var secondList = input.stream().map(Pair::second).sorted().toList();

        return new Pair<>(firstList, secondList);
    }
}
