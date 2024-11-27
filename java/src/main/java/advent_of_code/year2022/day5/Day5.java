package advent_of_code.year2022.day5;

import java.util.*;

class Day5 {

    String part1(List<String> lines) {
        var splitIndex = getSplitIndex(lines);
        var cratesCount = Integer.parseInt(Arrays.stream(lines.get(splitIndex - 1).split(" ")).toList().getLast());
        var crates = new Crates(lines, cratesCount, splitIndex);
        var instructions = lines.subList(splitIndex + 1, lines.size()).stream().map(Instruction::of).toList();

        crates.apply(instructions);

        return crates.top();
    }

    private static int getSplitIndex(List<String> lines) {
        int splitIndex = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                break;
            }
            splitIndex++;
        }
        return splitIndex;
    }

    public String part2(List<String> lines) {
        return null;
    }
}
