package advent_of_code.year2023.day1;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class Day1 {

    public Integer solve(List<String> lines) {
        return lines
            .stream()
            .filter(s -> !s.isBlank())
            .mapToInt(Day1::getCalibrationValue)
            .sum();
    }

    private static int getCalibrationValue(String row) {

        var result = row
            .chars()
            .mapToObj(c -> (char) c)
            .filter(Character::isDigit)
            .map(String::valueOf)
            .toList();

        var firstElement = first(result);
        var lastElement = last(result);

        return Integer.parseInt(firstElement + lastElement);
    }

    private static String first(List<String> result) {
        return result.get(0);
    }

    private static String last(List<String> result) {
        return result.get(result.size() - 1);
    }

    public Integer solvePart2(List<String> lines) {
        return lines
            .stream()
            .map(Day1::clearInput2)
            .filter(s -> !s.isBlank())
            .mapToInt(Day1::getCalibrationValue)
            .sum();
    }

    private static final Map<String, Integer> mapping = Map.ofEntries(
        Map.entry("one", 1),
        Map.entry("two", 2),
        Map.entry("three", 3),
        Map.entry("four", 4),
        Map.entry("five", 5),
        Map.entry("six", 6),
        Map.entry("seven", 7),
        Map.entry("eight", 8),
        Map.entry("nine", 9)
    );

    private static String clearInput(String row) {
        String updatedRow = row;
        var indexOfFirstNumberInLetters = indexOfFirstNumberInLetters(row);
        while (indexOfFirstNumberInLetters.isPresent()) {
            Index index = indexOfFirstNumberInLetters.get();
            updatedRow = updatedRow.replace(index.key(), String.valueOf(mapping.get(index.key())));
            indexOfFirstNumberInLetters = indexOfFirstNumberInLetters(updatedRow);
        }

        return updatedRow;
    }

    record Index(String key, int index) {}
    private static Optional<Index> indexOfFirstNumberInLetters(String row) {
        return mapping
            .keySet()
            .stream()
            .map(key -> new Index(key, row.indexOf(key)))
            .filter(idx -> idx.index() >= 0)
            .min((idx1, idx2) -> idx1.index - idx2.index);
    }

    private static String clearInput2(String row) {
        StringBuilder updatedRow = new StringBuilder();
        for (int i = 0; i < row.length(); i++) {
            String substring = row.substring(i);

            Optional<String> startingKey = mapping
                .keySet()
                .stream()
                .filter(substring::startsWith)
                .findFirst();

            if (startingKey.isPresent()) {
                updatedRow.append(mapping.get(startingKey.get()));
            } else {
                updatedRow.append(row.charAt(i));
            }
        }
        return updatedRow.toString();
    }
}
