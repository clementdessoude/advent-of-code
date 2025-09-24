package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    private StringUtils() {}

    public static boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }

    public static List<Character> chars(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }

        return result;
    }
}
