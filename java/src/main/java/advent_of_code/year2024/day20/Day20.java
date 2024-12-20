package advent_of_code.year2024.day20;

import java.util.List;

class Day20 {

    Long part1(List<String> lines, int picoSavedThreshold) {
        return new Racetrack(lines).cheatCount(picoSavedThreshold);
    }

    Long part2(List<String> lines, int picoSavedThreshold) {
        return new Racetrack(lines).cheatCountWithSize(picoSavedThreshold, 20);
    }
}
