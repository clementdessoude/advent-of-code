package advent_of_code.year2023.day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day14 {

    public int part1(List<String> lines) {
        return IntStream.range(0, lines.get(0).length()).map(i -> columnLoad(lines, i)).sum();
    }

    private static int columnLoad(List<String> lines, int columnIndex) {
        int columnLoad = 0;
        int currentPosition = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            char c = lines.get(i).charAt(columnIndex);

            if (c == 'O') {
                columnLoad += currentPosition;
                currentPosition -= 1;
            } else if (c == '#') {
                currentPosition = lines.size() - i - 1;
            }
        }

        return columnLoad;
    }

    record CycleCache(List<String> result, int setInCacheAt) {}

    record Cycle(List<String> result, Integer cycleSize) {}

    private Map<List<String>, CycleCache> roll = new HashMap<>();

    Cycle cycle(List<String> lines, int cycleNumber) {
        if (roll.containsKey(lines)) {
            return new Cycle(
                roll.get(lines).result(),
                cycleNumber - roll.get(lines).setInCacheAt()
            );
        }

        var tmp = lines;
        for (int i = 0; i < 4; i++) {
            tmp = roll(tmp);
        }

        roll.put(lines, new CycleCache(tmp, cycleNumber));

        return new Cycle(tmp, null);
    }

    private List<String> roll(List<String> lines) {
        return IntStream.range(0, lines.get(0).length())
            .mapToObj(j -> {
                int currentPosition = 0;

                List<String> characters = new ArrayList<>();
                for (int i = 0; i < lines.size(); i++) {
                    characters.add(".");
                }

                for (int i = 0; i < lines.size(); i++) {
                    char c = lines.get(i).charAt(j);

                    if (c == 'O') {
                        characters.set(currentPosition, "O");
                        currentPosition += 1;
                    } else if (c == '#') {
                        characters.set(i, "#");
                        currentPosition = i + 1;
                    }
                }

                return String.join("", characters.reversed());
            })
            .toList();
    }

    public Integer part2(List<String> lines) {
        var tmp = lines;
        int max_boucles = 1_000_000_000;
        for (int i = 0; i < max_boucles; i++) {
            var result = cycle(tmp, i);

            if (result.cycleSize() != null) {
                int k = (max_boucles - i) / result.cycleSize();
                i = i + k * result.cycleSize();
            }

            tmp = result.result();
        }

        List<String> finalTmp = tmp;
        return IntStream.range(0, lines.get(0).length()).map(j -> totalLoad(finalTmp, j)).sum();
    }

    private int totalLoad(List<String> lines, int columnIndex) {
        int columnLoad = 0;
        int currentPosition = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            char c = lines.get(i).charAt(columnIndex);

            if (c == 'O') {
                columnLoad += (lines.size() - i);
            }
        }

        return columnLoad;
    }
}
