package advent_of_code.year2024.day17;

import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;
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

        //        var program = new Program(instructions, 92570L, 0L, 0L);
        //        if (program.isRecursive(instructions)) {
        //            return null;
        //        }

        // 6_500_000_000L sans check sur modulo
        // 11710001171
        var result = LongStream.range(7_500_000_000L, 367_500_000_000L)
            .parallel()
            .filter(i -> {
                var program = new Program(instructions, i, 0L, 0L);
                return program.isRecursive(instructions);
            })
            .findFirst();
        //        for (long i = 11710001171L; i < 300_000_000_000L; i++) {
        //            var c = i % 8;
        //            if (c == 0 || c >= 4) {
        //                continue;
        //            }
        //            if (i % 10_000_001 == 0) {
        //                System.out.println(i);
        //                System.out.println(Instant.now().toEpochMilli() - before.toEpochMilli());
        //                before = Instant.now();
        //            }
        //            var program = new Program(instructions, i, 0L, 0L);
        //            if (program.isRecursive(instructions)) {
        //                return i;
        //            }
        //        }

        return result.orElseThrow();
    }
}
