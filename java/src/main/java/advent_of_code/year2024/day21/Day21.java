package advent_of_code.year2024.day21;

import java.util.List;

class Day21 {

    Long part1(List<String> lines) {
        return lines.stream().mapToLong(code -> new Solver(code).solve()).sum();
    }

    Long part2(List<String> lines) {
        return null;
    }
}
