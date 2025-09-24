package advent_of_code.year2020.day8;

import static java.lang.Integer.parseInt;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day8 {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile(
        "(?<instruction>(acc|jmp|nop)) (?<sign>([+\\-]))(?<value>(\\d+))$"
    );

    Long part1(List<String> lines) {
        Processor processor = parse(lines);
        processor.run();

        return processor.accumulator();
    }

    private static Processor parse(List<String> lines) {
        var processor = new Processor();

        for (var line : lines) {
            processor.add(parse(line));
        }

        return processor;
    }

    private static Instruction parse(String line) {
        Matcher matcher = INSTRUCTION_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid instruction: " + line);
        }
        var instruction = matcher.group("instruction");
        var sign = matcher.group("sign").equals("+") ? 1 : -1;
        var value = sign * parseInt(matcher.group("value"));

        return switch (instruction) {
            case "acc" -> new Accumulator(value);
            case "nop" -> new Noop();
            case "jmp" -> new Jump(value);
            default -> throw new IllegalArgumentException("Invalid instruction: " + line);
        };
    }

    Long part2(List<String> lines) {
        var processor = parse(lines);
        var debugged = processor.debug();
        return debugged.accumulator();
    }
}
