package advent_of_code.year2024.day25;

import java.util.ArrayList;
import java.util.List;

final class Key {

    private final List<Integer> heights;

    private Key(List<Integer> heights) {
        this.heights = heights;
    }

    static Key of(List<String> key) {
        var horizontalSize = key.getFirst().length();
        List<Integer> result = new ArrayList<>(horizontalSize);

        for (int i = 0; i < horizontalSize; i++) {
            result.add(-1);
        }

        for (int h = 0; h < key.size(); h++) {
            for (int i = 0; i < horizontalSize; i++) {
                if (key.get(h).charAt(i) == '#' && result.get(i) == -1) {
                    result.set(i, key.size() - h - 1);
                }
            }
        }
        return new Key(result);
    }

    public List<Integer> heights() {
        return heights;
    }

    public boolean fit(Lock lock) {
        int size = 5;

        for (int i = 0; i < heights.size(); i++) {
            if (heights.get(i) + lock.heights().get(i) > size) {
                return false;
            }
        }

        return true;
    }
}
