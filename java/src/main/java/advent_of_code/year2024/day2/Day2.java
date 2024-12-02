package advent_of_code.year2024.day2;

import java.util.Arrays;
import java.util.List;

class Day2 {

    Long part1(List<String> lines) {
        return lines
            .stream()
            .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
            .map(Report::new)
            .filter(Report::isSafe)
            .count();
    }

    Long part2(List<String> lines) {
        return null;
    }
}
