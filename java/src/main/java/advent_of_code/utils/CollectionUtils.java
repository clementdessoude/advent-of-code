package advent_of_code.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {}

    public static <T> List<Pair<T, T>> pairOf(Collection<T> collection) {
        var list = collection.stream().toList();
        List<Pair<T, T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                result.add(new Pair<>(list.get(i), list.get(j)));
            }
        }

        return result;
    }
}
