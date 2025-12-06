package advent_of_code.year2025.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Day6 {

    Long part1(List<String> lines) {
        var input = new Part1(lines);
        return input.solve();
    }

    Long part2(List<String> lines) {
        return new Part2(lines).solve();
    }

    private static class Part1 {

        private final List<List<Long>> numbers = new ArrayList<>();
        private final List<String> operations;

        Part1(List<String> lines) {
            for (int i = 0; i < lines.size() - 1; i++) {
                numbers.add(
                    Arrays.stream(lines.get(i).split("\\s+"))
                        .filter(s -> !s.isBlank())
                        .map(Long::parseLong)
                        .toList()
                );
            }

            operations = Arrays.stream(lines.getLast().split("\\s+")).toList();
        }

        Long solve() {
            Long result = 0L;
            for (int i = 0; i < operations.size(); i++) {
                int finalI = i;
                var operands = numbers.stream().map(line -> line.get(finalI)).toList();
                result += operations.get(i).equals("+") ? add(operands) : multiply(operands);
            }

            return result;
        }

        Long multiply(List<Long> numbers) {
            return numbers.stream().reduce(1L, (a, b) -> a * b);
        }

        Long add(List<Long> numbers) {
            return numbers.stream().reduce(0L, Long::sum);
        }
    }

    private static class Part2 {

        private final List<String> numbers;
        private final String operations;

        Part2(List<String> lines) {
            numbers = lines.subList(0, lines.size() - 1);
            operations = lines.getLast();
        }

        Long solve() {
            int maxLength = numbers
                .stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElseThrow();

            Long result = 0L;
            int index = 0;
            while (index < operations.length()) {
                char operator = operations.charAt(index);
                List<Long> operands = new ArrayList<>();
                do {
                    int finalIndex = index;
                    String rawValue = numbers
                        .stream()
                        .map(line ->
                            finalIndex >= line.length()
                                ? ""
                                : String.valueOf(line.charAt(finalIndex))
                        )
                        .filter(s -> !s.isBlank())
                        .collect(Collectors.joining());
                    if (!rawValue.isBlank()) {
                        Long value = Long.parseLong(rawValue);
                        operands.add(value);
                    }
                    index++;
                } while (
                    index < maxLength &&
                    (index >= operations.length() || operations.charAt(index) == ' ')
                );

                result += operator == '+' ? add(operands) : multiply(operands);
            }

            return result;
        }

        Long multiply(List<Long> numbers) {
            return numbers.stream().reduce(1L, (a, b) -> a * b);
        }

        Long add(List<Long> numbers) {
            return numbers.stream().reduce(0L, Long::sum);
        }
    }
}
