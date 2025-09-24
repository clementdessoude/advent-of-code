package advent_of_code.year2020.day9;

import java.util.List;

class Day9 {

    Long part1(List<String> lines, int preamble) {
        var data = lines.stream().map(Long::parseLong).toList();

        return new Xmas(data, preamble).firstInvalidNumber();
    }

    Long part2(List<String> lines, int preamble) {
        var data = lines.stream().map(Long::parseLong).toList();

        return new Xmas(data, preamble).encryptionWeakness();
    }
}
