package advent_of_code.year2022.day12;

import java.util.List;

class Day12 {

    int part1(List<String> lines) {
        return new Heightmap(lines).shortestPathFromStartingPoint();
    }

    int part2(List<String> lines) {
        return new Heightmap(lines).shortestPathFromAnyLowPoint();
    }
}
