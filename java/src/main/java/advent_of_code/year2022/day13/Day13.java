package advent_of_code.year2022.day13;

import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.List;

class Day13 {

    int part1(List<String> lines) {
        List<Pair<CustomList, CustomList>> pairs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            var first = parse(lines.get(i));
            var second = parse(lines.get(i + 1));
            pairs.add(new Pair<>(first, second));
        }

        int result = 0;
        for (int i = 0; i < pairs.size(); i++) {
            var pair = pairs.get(i);
            if (pair.first().compare(pair.second()) <= 0) {
                result += (i + 1);
            }
        }

        return result;
    }

    private CustomList parse(String row) {
        List<Item> result = new ArrayList<>();
        var previous = result;
        var current = result;
        var sb = new StringBuilder();
        for (int i = 1; i < row.length(); i++) {
            char c = row.charAt(i);
            if (c == '[') {
                List<Item> newItems = new ArrayList<>();
                current.add(new CustomList(newItems));
                previous = current;
                current = newItems;
            } else if (c == ']') {
                if (!sb.isEmpty()) {
                    current.add(new Value(Integer.parseInt(sb.toString())));
                    sb = new StringBuilder();
                }
                current = previous;
            } else if (c == ',') {
                if (!sb.isEmpty()) {
                    current.add(new Value(Integer.parseInt(sb.toString())));
                    sb = new StringBuilder();
                }
            } else {
                sb.append(c);
            }
        }

        return new CustomList(result);
    }

    int part2(List<String> lines) {
        var linesWithDividers = new ArrayList<>(lines);
        String dividerOneDefinition = "[[2]]";
        linesWithDividers.add(dividerOneDefinition);
        String dividerTwoDefinition = "[[6]]";
        linesWithDividers.add(dividerTwoDefinition);
        var sorted = linesWithDividers
            .stream()
            .filter(line -> !line.isEmpty())
            .map(this::parse)
            .sorted(CustomList::compare)
            .toList();

        var dividerOne = parse(dividerOneDefinition);
        var dividerTwo = parse(dividerTwoDefinition);

        return ((sorted.indexOf(dividerOne) + 1) * (sorted.indexOf(dividerTwo) + 1));
    }
}
