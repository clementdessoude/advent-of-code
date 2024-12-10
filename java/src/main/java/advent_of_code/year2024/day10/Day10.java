package advent_of_code.year2024.day10;

import advent_of_code.utils.Location;
import java.util.List;

class Day10 {

    Long part1(List<String> lines) {
        TopographicMap topographicMap = new TopographicMap(lines);
        return topographicMap.totalScore();
    }

    Long part2(List<String> lines) {
        TopographicMap topographicMap = new TopographicMap(lines);
        return topographicMap.totalRating();
    }
}
