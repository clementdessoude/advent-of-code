
package advent_of_code.year2023.day12;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {

    public Long part1(List<String> lines) {
        return lines
                .stream()
                .mapToLong(Day12::numberOfArrangements)
                .sum();
    }

    private static long numberOfArrangements(String row) {
        String[] split = row.split(" ");
        var pattern = split[0];
        var sources = Arrays
                .stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();

        return numberOfArrangements(pattern, sources);
    }

    record Cache(
            String pattern,
            List<Integer> brokenSources
    ) {}
    private static final Map<Cache, Long> cache = new HashMap<>();

    private static long numberOfArrangements(
            String pattern,
            List<Integer> brokenSources
    ) {
        var cacheKey = new Cache(pattern, brokenSources);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }


        int idxOfBrokenSource = pattern.indexOf('#');
        if (brokenSources.isEmpty()) {
            int result = idxOfBrokenSource == -1 ? 1 : 0;

            cache.put(cacheKey, (long) result);

            return result;
        }

        if (pattern.length()
                < brokenSources.stream().mapToInt(i -> i).sum()
                + brokenSources.size() - 1) {

            cache.put(cacheKey, 0L);

            return 0;
        }

        int idxOfUnknownSource = pattern.indexOf('?');
        if (idxOfBrokenSource == -1 && idxOfUnknownSource == -1) {
            return 0;
        }

        if (idxOfUnknownSource == -1 || (idxOfBrokenSource < idxOfUnknownSource
                && idxOfBrokenSource != -1
        )) {
            long result = considerNextAsBrokenSource(
                    pattern,
                    brokenSources,
                    idxOfBrokenSource
            );
            cache.put(cacheKey, result);
            return result;
        }

        // Consider as brokenSource
        var optionIfBrokenSource = considerNextAsBrokenSource(
                pattern,
                brokenSources,
                idxOfUnknownSource
        );

        Cache key1 = new Cache(
                pattern.substring(idxOfUnknownSource + 1),
                brokenSources
        );

        var optionIfNotBrokenSource = cache.containsKey(key1) ? cache.get(key1) : numberOfArrangements(pattern.substring(
                idxOfUnknownSource + 1), brokenSources);
        cache.put(key1, optionIfNotBrokenSource);

        long result = optionIfBrokenSource + optionIfNotBrokenSource;
        cache.put(cacheKey, result);
        return result;
    }

    private static long considerNextAsBrokenSource(
            String pattern,
            List<Integer> brokenSources,
            int idxOfBrokenSource
    ) {
        var firstBrokenSourceExpectedCount = brokenSources.get(0);

        int i = idxOfBrokenSource;

        if (idxOfBrokenSource + firstBrokenSourceExpectedCount
                > pattern.length()) {
            return 0;
        }

        while (i < idxOfBrokenSource + firstBrokenSourceExpectedCount) {
            if (pattern.charAt(i) == '.') {
                return 0;
            }
            i++;
        }
        if (i == pattern.length()) {
            return brokenSources.size() == 1 ? 1 : 0;
        }

        if (pattern.charAt(i) == '#') {
            return 0;
        }

        String substring = pattern.substring(i + 1);
        List<Integer> brokenSources1 = brokenSources.subList(
                1,
                brokenSources.size()
        );
        long result = numberOfArrangements(
                substring,
                brokenSources1
        );
        cache.put(new Cache(substring, brokenSources1), result);
        return result;
    }

    public Long part2(List<String> lines) {
        var unfolds = lines
                .stream()
                .map(Day12::unfold)
                .toList();

        var sum = 0L;
        for (int i = 0; i < lines.size(); i++) {
            long l = numberOfArrangements(unfolds.get(i));
            sum += l;
        }

        return sum;
    }

    private static String unfold(String row) {
        String[] split = row.split(" ");
        String pattern = IntStream.range(0, 5)
                                  .mapToObj(i -> split[0])
                                  .collect(Collectors.joining("?"));
        var sources = IntStream.range(0, 5)
                               .mapToObj(i -> split[1])
                               .collect(Collectors.joining(","));

        return pattern + " " + sources;
    }
}

