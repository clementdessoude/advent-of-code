package advent_of_code.year2024.day2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

class Day2 {

    Long part1(List<String> lines) {
        return parseReports(lines).filter(Report::isSafe).count();
    }

    private static Stream<Report> parseReports(List<String> lines) {
        return lines
            .stream()
            .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
            .map(Report::new);
    }

    Long part2(List<String> lines) {
        return parseReports(lines).filter(Report::isSafeWithDampenerProblem).count();
    }
}
