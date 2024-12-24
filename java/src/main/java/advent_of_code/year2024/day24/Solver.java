package advent_of_code.year2024.day24;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.assertj.core.util.TriFunction;

final class Solver {

    private static final Pattern OPERATION_PATTERN = Pattern.compile(
        "^(?<first>\\w+) (?<operation>\\w+) (?<second>\\w+) -> (?<output>\\w+)$"
    );

    private final Map<String, Boolean> wires = new HashMap<>();
    private final Map<String, Supplier<Boolean>> operations = new HashMap<>();

    public Solver(List<String> lines) {
        int blankLineIndex = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                blankLineIndex = i;
                break;
            }

            var split = line.split(": ");
            wires.put(split[0], "1".equals(split[1]));
        }

        for (int i = blankLineIndex + 1; i < lines.size(); i++) {
            Matcher matcher = OPERATION_PATTERN.matcher(lines.get(i));
            matcher.matches();

            String operation = matcher.group("operation");
            String output = matcher.group("output");
            String first = matcher.group("first");
            String second = matcher.group("second");

            operations.put(output, getOutputValueProvider(first, second, operation, output));
        }
    }

    long solve() {
        List<String> sortedWires = Stream.concat(
            wires.keySet().stream(),
            operations.keySet().stream()
        )
            .filter(wire -> wire.startsWith("z"))
            .distinct()
            .sorted()
            .toList();

        var result = 0L;
        for (int i = 0; i < sortedWires.size(); i++) {
            String wireName = sortedWires.get(i);
            long bitValue = getValue(wireName) ? 1L : 0L;
            result += bitValue << i;
        }

        return result;
    }

    private Supplier<Boolean> getOutputValueProvider(String first, String second, String operation, String output) {
        return () -> {
            Boolean firstValue = getValue(first);
            Boolean secondValue = getValue(second);

            var result = switch (operation) {
                case "AND" -> firstValue && secondValue;
                case "OR" -> firstValue || secondValue;
                case "XOR" -> firstValue != secondValue;
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            };

            wires.put(output, result);
            return result;
        };
    }

    private Boolean getValue(String wireName) {
        Boolean value = wires.get(wireName);
        if (value != null) {
            return value;
        }

        return operations.get(wireName).get();
    }
}
