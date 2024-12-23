package advent_of_code.year2024.day21;

import advent_of_code.utils.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day21 {

    Long part1(List<String> lines) {
        Map<Pair<String, String>, Map<Integer, Long>> cache = new HashMap<>();
        return lines.stream().mapToLong(code -> new Solver(code).complexity(3, cache)).sum();
    }

    Long part2(List<String> lines) {
        Map<Pair<String, String>, Map<Integer, Long>> cache = new HashMap<>();
        return lines.stream().mapToLong(code -> new Solver(code).complexity(26, cache)).sum();
    }
}
