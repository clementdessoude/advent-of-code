package advent_of_code.year2024.day24;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class Solver {

    public static final Pattern OPERATION_PATTERN = Pattern.compile(
        "^(?<first>\\w+) (?<operation>\\w+) (?<second>\\w+) -> (?<output>\\w+)$"
    );

    private final Map<String, Boolean> wires = new HashMap<>();
    private final Map<String, Supplier<Boolean>> operations = new HashMap<>();
    private final Map<Set<String>, String> outputs = new HashMap<>();
    private final Map<String, String> swaps = Map.of(
        "dbp", "fdv",
        "fdv", "dbp",
        "ckj", "z15",
        "z15", "ckj",
        "kdf", "z23",
        "z23", "kdf",
        "rpp", "z39",
        "z39", "rpp"
    );

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
            outputs.put(
                Set.of(
                    first,
                    second,
                    operation
                ),
                swaps.getOrDefault(output, output)
            );
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

    String solvePart2() {
        Map<Integer, String> carries = new HashMap<>();
        carries.put(0, "rfg");
        for (int i = 1; i < 45; i++) {
            String z = String.format("z%02d", i);
            String x = String.format("x%02d", i);
            String y = String.format("y%02d", i);
            String xor = getOutput(x, y, "XOR");
            String previousCarry = carries.get(i - 1);
            String output = getOutput(
                previousCarry,
                xor,
                "XOR"
            );
            if (!z.equals(output)) {
                System.out.println("%s - %s".formatted(output, z));
                throw new IllegalStateException(z);
            }
            // pi+1 = (xi & yi) | (pi&(xi^yi))
            String carryXorPreviousXor = getOutput(previousCarry, xor, "AND");
            if (carryXorPreviousXor == null) {
                System.out.println("Error: " + previousCarry + " - " + xor);
                throw new IllegalStateException(z);
            }
            var carry = getOutput(getOutput(x, y, "AND"), carryXorPreviousXor, "OR");
            if (carry == null) {
                System.out.println("Error " + z);
                throw new IllegalStateException(z);
            }
            carries.put(i, carry);
        }

        return swaps.keySet().stream().sorted().collect(Collectors.joining(","));
    }

    private String getOutput(String one, String two, String operation) {
        return outputs.get(Set.of(one, two, operation));
    }

    private Boolean getValue(String wireName) {
        Boolean value = wires.get(wireName);
        if (value != null) {
            return value;
        }

        return operations.get(wireName).get();
    }
}
