package advent_of_code.year2022.day9;

import java.util.List;

class Day9 {

    int part1(List<String> lines) {
        var instructions = parse(lines);
        var grid = new Grid(instructions);

        return grid.process();
    }

    private List<Instruction> parse(List<String> lines) {
        return lines
            .stream()
            .map(s -> s.split(" "))
            .map(split -> new Instruction(from(split[0]), Integer.parseInt(split[1])))
            .toList();
    }

    private Instruction.Direction from(String split) {
        return switch (split) {
            case "L" -> Instruction.Direction.LEFT;
            case "R" -> Instruction.Direction.RIGHT;
            case "U" -> Instruction.Direction.UP;
            case "D" -> Instruction.Direction.DOWN;
            default -> throw new IllegalArgumentException();
        };
    }

    Long part2(List<String> lines) {
        return null;
    }
}
