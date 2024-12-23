package advent_of_code;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Utils {

    private Utils() {}

    public static final Function<String, String[]> COMMA_SEPARATOR = line -> line.split(",");

    public static void print(List<List<String>> map) {
        String printed = map
            .stream()
            .map(r -> String.join("", r))
            .collect(Collectors.joining("\n"));
        System.out.println(printed);
    }

    public static void csv(List<List<String>> map) {
        String printed = map
            .stream()
            .map(r -> String.join(",", r))
            .collect(Collectors.joining("\n"));
        System.out.println(printed);
    }
}
