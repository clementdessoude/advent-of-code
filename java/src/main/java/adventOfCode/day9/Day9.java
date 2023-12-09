package adventOfCode.day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    public Long part1(List<String> lines) {
        return lines.stream()
            .map(Day9::parseRow)
            .mapToLong(Day9::nextVal)
            .sum();
    }

    private static List<Long> parseRow(String row) {
        return Arrays.stream(row.split(" ")).map(Long::parseLong).toList();
    }

    private static long nextVal(List<Long> longs) {
        List<Long> sublist = new ArrayList<>();
        for (int i = 0; i < longs.size() - 1; i++) {
            sublist.add(longs.get(i + 1) - longs.get(i));
        }

        long nextSubListValue = sublist.stream().allMatch(l -> l == 0) ? 0 : nextVal(sublist);

        return longs.get(longs.size() - 1) + nextSubListValue;
    }

    public Long part2(List<String> lines) {
        return lines.stream()
            .map(Day9::parseRow)
            .mapToLong(Day9::prevValue)
            .sum();
    }

    private static long prevValue(List<Long> longs) {
        List<Long> sublist = new ArrayList<>();
        for (int i = 0; i < longs.size() - 1; i++) {
            sublist.add(longs.get(i + 1) - longs.get(i));
        }

        long prevValue = sublist.stream().allMatch(l -> l == 0) ? 0 : prevValue(sublist);

        return longs.get(0) - prevValue;
    }
}
