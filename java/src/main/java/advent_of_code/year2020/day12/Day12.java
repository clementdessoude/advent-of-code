package advent_of_code.year2020.day12;

import java.util.List;

import static java.lang.Integer.parseInt;

class Day12 {

    Long part1(List<String> lines) {
        var ferry = new Ferry(parse(lines));
        ferry.run();
        return ferry.distance();
    }

    private static List<Instruction> parse(List<String> lines) {
        return lines.stream().map(Day12::parse).toList();
    }

    private static Instruction parse(String line) {
        var operation = line.charAt(0);
        var value = parseInt(line.substring(1));

        return switch (operation) {
            case 'N' -> new North(value);
            case 'S' -> new South(value);
            case 'E' -> new East(value);
            case 'W' -> new West(value);
            case 'F' -> new Forward(value);
            case 'L' -> new Left(value);
            case 'R' -> new Right(value);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }

    Long part2(List<String> lines) {
        var ferry = new Ferry(parse(lines));
        ferry.runPart2();
        return ferry.distance();
    }
}
