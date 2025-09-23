package advent_of_code.year2020.day5;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Day5 {

    Long part1(List<String> lines) {
        return maxSeatId(lines);
    }

    Long part2(List<String> lines) {
        var maxSeatId = maxSeatId(lines);
        var seatIds = lines
                .stream()
                .map(BoardingPass::new)
                .map(BoardingPass::seatId)
                .collect(Collectors.toSet());

        for (long i = 0; i < maxSeatId; i++) {
            if (!seatIds.contains(i) && seatIds.contains(i + 1) && seatIds.contains(i - 1)) {
                return i;
            }

        }

        return null;
    }

    private static Long maxSeatId(List<String> lines) {
        return lines
                .stream()
                .map(BoardingPass::new)
                .max(Comparator.comparingLong(BoardingPass::seatId))
                .map(BoardingPass::seatId)
                .orElseThrow();
    }
}
