package advent_of_code.year2024.day16;

import java.util.List;

class Day16 {

    Long part1(List<String> lines) {
        return new Maze(lines).shortestDistance();
    }

    Long part2(List<String> lines) {
        return new Maze(lines).numberOfTilesOnShortestPath();
    }
}
