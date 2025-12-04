package advent_of_code.year2025.day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToLongFunction;

class Day3 {

    Long part1(List<String> lines) {
        ToLongFunction<List<Integer>> first = (List<Integer> digits) -> maxJoltage(digits, 2);
        return lines
            .stream()
            .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).toList())
            .mapToLong(first)
            .sum();
    }

    Long part2(List<String> lines) {
        ToLongFunction<List<Integer>> first = (List<Integer> digits) -> maxJoltage(digits, 12);
        return lines
            .stream()
            .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).toList())
            .mapToLong(first)
            .sum();
    }

    Long maxJoltage(List<Integer> digits, int numberOfBatteries) {
        List<String> result = new ArrayList<>();

        var currentIndex = 0;
        for (int n = 0; n < numberOfBatteries; n++) {
            var maxIndex = currentIndex;
            var maxJoltage = 0;
            for (int i = maxIndex; i < digits.size() - (numberOfBatteries - n - 1); i++) {
                if (digits.get(i) > maxJoltage) {
                    maxJoltage = digits.get(i);
                    maxIndex = i;
                }
            }
            currentIndex = maxIndex + 1;
            result.add(String.valueOf(maxJoltage));
        }

        return Long.parseLong(String.join("", result));
    }
}
