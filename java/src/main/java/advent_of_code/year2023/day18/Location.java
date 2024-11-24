package advent_of_code.year2023.day18;

import java.util.ArrayList;
import java.util.List;

record Location(int i, int j) {
    <T> List<Location> adjacent(List<List<T>> lines) {
        List<Location> result = new ArrayList<>();
        if (i > 0) {
            result.add(new Location(i - 1, j));
        }
        if (i < lines.size() - 1) {
            result.add(new Location(i + 1, j));
        }

        if (j > 0) {
            result.add(new Location(i, j - 1));
        }
        if (j < lines.get(0).size() - 1) {
            result.add(new Location(i, j + 1));
        }

        return result;
    }
}
