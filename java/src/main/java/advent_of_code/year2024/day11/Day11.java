package advent_of_code.year2024.day11;

import java.util.Arrays;
import java.util.List;

class Day11 {

    long part1(List<String> lines) {
        return part1(lines.getFirst());
    }

    long part1(String line) {
        return resolve(line, 25);
    }

    static long resolve(String line, int blinkCount) {
        List<Stone> stones = Arrays.stream(line.split(" "))
            .map(Integer::parseInt)
            .map(Stone::new)
            .toList();

        return stones
            .stream()
            .mapToLong(stone -> stone.countHowManyStonesHasProducedAfter(blinkCount))
            .sum();
    }

    long part2(List<String> lines) {
        return resolve(lines.getFirst(), 75);
    }
}
