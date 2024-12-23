package advent_of_code.year2024.day17;

import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class Day17 {

    String part1(List<String> lines) {
        var registerA = parseRegister(lines.getFirst());
        var registerB = parseRegister(lines.get(1));
        var registerC = parseRegister(lines.get(2));
        var instructions = parseInstruction(lines.getLast());

        var program = new Program(instructions, registerA, registerB, registerC);

        return program.output();
    }

    private List<Long> parseInstruction(String row) {
        String[] split = row.split(" ");
        String program = split[split.length - 1];

        return Arrays.stream(program.split(",")).map(Long::parseLong).toList();
    }

    private static Long parseRegister(String row) {
        String[] split = row.split(" ");
        return Long.parseLong(split[split.length - 1]);
    }

    Long part2(List<String> lines) {
        var instructions = parseInstruction(lines.getLast());
        return new SolverPart2(instructions).solvePart2();
    }
}
