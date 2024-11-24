
package advent_of_code.year2023.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 {

    public Long part1(List<String> lines) {
        return solve(lines, 2);
    }

    private static long solve(List<String> lines, int expansionFactor) {
        List<Integer> emptyRows = IntStream
            .range(0, lines.size())
            .filter(i -> lines.get(i).matches("\\.*"))
            .boxed()
            .toList();

        List<Integer> emptyColumns = IntStream
            .range(0, lines.get(0).length())
            .filter(j -> lines
                .stream()
                .map(line -> line.charAt(j))
                .allMatch(c -> c == '.'))
            .boxed()
            .toList();

        long sum = 0;
        List<Galaxy> galaxies = new ArrayList<>();
        int emptyRowCount = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (emptyRowCount < emptyRows.size() && emptyRows.get(emptyRowCount) == i) {
                emptyRowCount++;
                continue;
            }
            int emptyColumnCount = 0;
            for (int j = 0; j < lines.get(0).length(); j++) {
                if (emptyColumnCount < emptyColumns.size() && emptyColumns.get(emptyColumnCount) == j) {
                    emptyColumnCount++;
                    continue;
                }
                if (lines.get(i).charAt(j) == '#') {
                    Galaxy newGalaxy = new Galaxy(i + emptyRowCount * (expansionFactor - 1), j + emptyColumnCount * (expansionFactor - 1));
                    sum += galaxies.stream().mapToLong(g -> g.distanceTo(newGalaxy)).sum();
                    galaxies.add(newGalaxy);
                }
            }
        }

        return sum;
    }

    public Long part2(List<String> lines, int expansionFactor) {
        return solve(lines, expansionFactor);
    }
}

