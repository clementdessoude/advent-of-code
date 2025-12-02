package advent_of_code.year2025.day2;

import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day2 {

    private static final List<String> patterns = patterns();

    Long part1(List<String> lines) {
        return parse(lines.getFirst()).stream().mapToLong(this::sumOfPatterns).sum();
    }

    List<Pair<Long, Long>> parse(String input) {
        return Arrays.stream(input.split(","))
            .map(range -> {
                var split = range.split("-");
                return new Pair<>(Long.parseLong(split[0]), Long.parseLong(split[1]));
            })
            .toList();
    }

    long sumOfPatterns(Pair<Long, Long> range) {
        return patterns
            .stream()
            .map(pattern -> match1(range, pattern))
            .flatMap(List::stream)
            .mapToLong(Long::longValue)
            .sum();
    }

    List<Long> match1(Pair<Long, Long> range, String pattern) {
        var value = Long.parseLong(pattern + pattern);

        if (value >= range.first() && value <= range.second()) {
            return List.of(value);
        }

        return List.of();
    }

    private static List<String> patterns() {
        var patternsOfOne = Arrays.stream("123456789".split("")).toList();
        var patternsOfTwo = patternsWithExtraDigit(patternsOfOne);
        var patternsOfThree = patternsWithExtraDigit(patternsOfTwo);
        var patternsOfFour = patternsWithExtraDigit(patternsOfThree);
        var patternsOfFive = patternsWithExtraDigit(patternsOfFour);

        List<String> result = new ArrayList<>();
        result.addAll(patternsOfOne);
        result.addAll(patternsOfTwo);
        result.addAll(patternsOfThree);
        result.addAll(patternsOfFour);
        result.addAll(patternsOfFive);

        return result;
    }

    private static Stream<String> digits() {
        return Arrays.stream("0123456789".split(""));
    }

    private static List<String> patternsWithExtraDigit(List<String> previousPatterns) {
        return previousPatterns
            .stream()
            .flatMap(pattern -> digits().map(d -> pattern + d))
            .toList();
    }

    Long part2(List<String> lines) {
        return parse(lines.getFirst()).stream().mapToLong(this::sumOfPatterns2).sum();
    }

    long sumOfPatterns2(Pair<Long, Long> range) {
        var uniques = patterns
            .stream()
            .map(pattern -> match2(range, pattern))
            .flatMap(List::stream)
            .collect(Collectors.toSet());

        return uniques.stream().mapToLong(Long::longValue).sum();
    }

    List<Long> match2(Pair<Long, Long> range, String pattern) {
        String currentPattern = pattern + pattern;
        long value = Long.parseLong(currentPattern);
        while (value < range.first()) {
            currentPattern += pattern;
            value = Long.parseLong(currentPattern);
        }

        List<Long> result = new ArrayList<>();
        while (value <= range.second()) {
            result.add(value);
            currentPattern += pattern;
            value = Long.parseLong(currentPattern);
        }

        return result;
    }
}
