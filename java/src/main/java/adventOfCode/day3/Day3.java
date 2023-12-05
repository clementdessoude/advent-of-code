package adventOfCode.day3;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;

import java.util.*;

public class Day3 {

    Integer part1(List<String> lines) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var row = lines.get(i);

            for (int j = 0; j < row.length(); j++) {
                if (Character.isDigit(row.charAt(j)) && hasAdjacentSymbol(i, j, lines)) {
                    var response = getRelatedNumber(i, j, lines);
                    integers.add(response.response);

                    j+= response.decalage;
                }
            }
        }

        return integers.stream().mapToInt(i -> i).sum();
    }

    private boolean hasAdjacentSymbol(int i, int j, List<String> lines) {
        if (i >= 1) {
            if (hasSymbolOnRow(j, i - 1, lines)) {
                return true;
            }
        }

        if (hasSymbolOnRow(j, i, lines)) {
            return true;
        }

        if (i < lines.size() - 1) {
            return hasSymbolOnRow(j, i + 1, lines);
        }

        return false;
    }

    private boolean hasSymbolOnRow(
        int j,
        int i,
        List<String> lines
    ) {
        int rowSize = lines.get(0).length();
        if (j >= 1) {
            if (isSymbol(i, j - 1, lines)) {
                return true;
            }
        }
        if (isSymbol(i, j, lines)) {
            return true;
        }
        if (j < rowSize - 1) {
            if (isSymbol(i, j + 1, lines)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSymbol(int i, int j, List<String> lines) {
        char c = lines.get(i).charAt(j);
        return c != '.' && !Character.isDigit(c);
    }

    record GetRelatedNumber(int response, int decalage) {}
    private GetRelatedNumber getRelatedNumber(int i, int j, List<String> lines) {
        var row = lines.get(i);

        Deque<Character> chars = new LinkedList<>();
        var position = j;
        do {
            chars.addFirst(row.charAt(position));
            position -= 1;
        } while (position >= 0 && Character.isDigit(row.charAt(position)));

        position = j + 1;
        while(position < row.length() && Character.isDigit(row.charAt(position))) {
            chars.add(row.charAt(position));
            position += 1;
        }

        int relatedNumber = parseInt(chars
                              .stream()
                              .map(String::valueOf)
                              .collect(joining()));
        return new GetRelatedNumber(relatedNumber, position - j);
    }

    public Integer part2(List<String> lines) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var row = lines.get(i);

            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '*') {
                    var adjacents = getAdjacentNumbers(i, j, lines);

                    var isGear = adjacents.size() >= 2;
                    if (isGear) {
                        integers.add(adjacents.stream().reduce(1, (a,b) -> a * b));
                    }
                }
            }
        }

        return integers.stream().mapToInt(i -> i).sum();
    }

    private List<Integer> getAdjacentNumbers(int i, int j, List<String> lines) {
        List<Integer> adjacents = new ArrayList<>();
        if (i >= 1) {
            adjacents.addAll(getAdjacentNumbersOnRow( j, i - 1, lines));
        }

        if (hasSymbolOnRow(j, i, lines)) {
            adjacents.addAll(getAdjacentNumbersOnRow( j, i, lines));
        }

        if (i < lines.size() - 1) {
            adjacents.addAll(getAdjacentNumbersOnRow( j, i + 1, lines));
        }

        return adjacents;
    }

    private List<Integer> getAdjacentNumbersOnRow(
        int j,
        int i,
        List<String> lines
    ) {
        int rowSize = lines.get(0).length();

        if (Character.isDigit(lines.get(i).charAt(j))) {
            return List.of(getRelatedNumber(i, j, lines).response);
        } else {
            List<Integer> adjacents = new ArrayList<>();
            if (j >= 1 && Character.isDigit(lines.get(i).charAt(j - 1))) {
                adjacents.add(getRelatedNumber(i, j - 1, lines).response);
            }
            if (j < rowSize - 1 && Character.isDigit(lines.get(i).charAt(j + 1))) {
                adjacents.add(getRelatedNumber(i, j + 1, lines).response);
            }
            return adjacents;
        }
    }
}
