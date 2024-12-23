package advent_of_code.year2023.day22;

import java.util.HashSet;
import java.util.Set;

public final class Utils {

    private Utils() {}

    public static <T> Set<T> intersection(Set<T> first, Set<T> second) {
        Set<T> result = new HashSet<>();

        first.stream().filter(second::contains).forEach(result::add);
        second.stream().filter(first::contains).forEach(result::add);

        return result;
    }

    public static <T> Set<T> union(Set<T> first, Set<T> second) {
        Set<T> result = new HashSet<>();

        result.addAll(first);
        result.addAll(second);

        return result;
    }
}
