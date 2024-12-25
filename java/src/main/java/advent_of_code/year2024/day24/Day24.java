package advent_of_code.year2024.day24;

import static advent_of_code.year2024.day24.Solver.OPERATION_PATTERN;

import java.util.*;
import java.util.regex.Matcher;

class Day24 {

    Long part1(List<String> lines) {
        return new Solver(lines).solve();
    }

    String part2(List<String> lines) {
        return new Solver(lines).solvePart2();
    }
}
