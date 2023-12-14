
package adventOfCode.day14;

import java.util.List;
import java.util.stream.IntStream;

public class Day14 {

    public int part1(List<String> lines) {
        return IntStream
                .range(0, lines.get(0).length())
                .map(i -> columnLoad(lines, i))
                .sum();
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

    public Long part2(List<String> lines) {
        return null;
    }
}

