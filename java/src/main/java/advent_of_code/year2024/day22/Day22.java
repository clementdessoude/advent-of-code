package advent_of_code.year2024.day22;

import java.util.List;
import java.util.stream.Collectors;

class Day22 {

    Long part1(List<String> lines) {
        return lines.stream().mapToLong(Long::parseLong).map(Solver::solve).sum();
    }

    Long part2(List<String> lines) {
        return new Solver().maxBananas(lines.stream().map(Long::parseLong).toList());
    }
}
