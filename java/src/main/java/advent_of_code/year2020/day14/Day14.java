package advent_of_code.year2020.day14;

import java.util.List;

class Day14 {

    Long part1(List<String> lines) {
        var instructions = parse(lines);
        return new Program(instructions).run();
    }

    private static List<Instruction> parse(List<String> strings) {
        return strings.stream().map(Day14::parse).toList();
    }

    private static Instruction parse(String input) {
        if (input.startsWith("mem")) {
            return parseAssignment(input);
        }

        return new Mask(input.replace("mask = ", ""));
    }

    private static Instruction parseAssignment(String input) {
        String[] split = input.split(" = ");
        var memoryAddress = Integer.parseInt(split[0].replaceAll("[\\[me\\]]", ""));
        var value = Long.parseLong(split[1]);
        return new Assignment(memoryAddress, value);
    }

    Long part2(List<String> lines) {
        return null;
    }
}
