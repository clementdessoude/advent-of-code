package advent_of_code.utils;

public final class StringUtils {

    private StringUtils() {}

    public static boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }
}
