package advent_of_code.year2024.day25;

import java.util.ArrayList;
import java.util.List;

final class Lock {

    private final List<Integer> heights;

    private Lock(List<Integer> heights) {
        this.heights = heights;
    }

    static Lock of(List<String> key) {
        var horizontalSize = key.getFirst().length();
        List<Integer> result = new ArrayList<>(horizontalSize);

        for (int i = 0; i < horizontalSize; i++) {
            result.add(-1);
        }

        for (int h = 1; h < key.size(); h++) {
            for (int i = 0; i < horizontalSize; i++) {
                if (key.get(h).charAt(i) == '.' && result.get(i) == -1) {
                    result.set(i, h - 1);
                }
            }
        }
        return new Lock(result);
    }

    public List<Integer> heights() {
        return heights;
    }
}
