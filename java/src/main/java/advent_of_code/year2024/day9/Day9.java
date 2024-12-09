package advent_of_code.year2024.day9;

import static java.util.Arrays.stream;

import java.util.List;

class Day9 {

    Long part1(List<String> lines) {
        return part1(lines.getFirst());
    }

    Long part1(String line) {
        return parse(line).compact().checksum();
    }

    private DiskMap parse(String line) {
        var numbers = stream(line.split("")).map(Integer::parseInt).toList();
        return new DiskMap(numbers);
    }

    Long part2(List<String> lines) {
        return null;
    }
}
