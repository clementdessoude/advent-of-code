
package adventOfCode.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    private static long numberOfArrangements(
            String pattern,
            List<Integer> brokenSources
    ) {
        int idxOfBrokenSource = pattern.indexOf('#');
        if (brokenSources.isEmpty()) {
            return idxOfBrokenSource == -1 ? 1 : 0;
        }

        if (pattern.length()
                < brokenSources.stream().mapToInt(i -> i).sum()
                + brokenSources.size() - 1) {
            return 0;
        }

        int idxOfUnknownSource = pattern.indexOf('?');
        if (idxOfBrokenSource == -1 && idxOfUnknownSource == -1) {
            return 0;
        }

        if (idxOfUnknownSource == -1 || (idxOfBrokenSource < idxOfUnknownSource
                && idxOfBrokenSource != -1
        )) {
            return considerNextAsBrokenSource(
                    pattern,
                    brokenSources,
                    idxOfBrokenSource
            );
        }

        // Consider as brokenSource
        var optionIfBrokenSource = considerNextAsBrokenSource(
                pattern,
                brokenSources,
                idxOfUnknownSource
        );
        var optionIfNotBrokenSource = numberOfArrangements(pattern.substring(
                idxOfUnknownSource + 1), brokenSources);

        return optionIfBrokenSource + optionIfNotBrokenSource;
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

        return numberOfArrangements(
                pattern.substring(i + 1),
                brokenSources.subList(
                        1,
                        brokenSources.size()
                )
        );
    }

    public Long part2(List<String> lines) {
        var unfolds = lines
                .stream()
                .map(Day12::unfold)
                .toList();
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("Line "
                                       + i
                                       + ": "
                                       + numberOfArrangements(unfolds.get(i)));
        }
        return null;
    }

    public Long part2Threads(List<String> lines) {
        var unfolds = lines
                .stream()
                .map(Day12::unfold)
                .toList();

        List<Future<String>> futureTasks = new ArrayList<>();
        // Executor (Chaque nouvelle Task démarre un nouveau Virtual Thread)
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            IntStream.range(50, lines.size()).parallel()
                    .filter(i -> lines.get(i).startsWith("#") || lines.get(i).endsWith("#"))
                     .forEach(i -> {

                // Submit Task + Démarre nouveau Virtual Thread
                Future<String> futureTask = executorService.submit(() -> "Line "
                        + i
                        + ": "
                        + numberOfArrangements(unfolds.get(i)));

                futureTasks.add(futureTask);
            });

            futureTasks.forEach(futureTask -> {
                try {

                    System.out.println(futureTask.get());

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return null;
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

