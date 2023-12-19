package adventOfCode.day19;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Part {
    private final Map<Category, Integer> description;
    private static final Pattern PART_PATTERN = Pattern.compile("^\\{x=(?<x>\\d+),m=(?<m>\\d+),a=(?<a>\\d+),s=(?<s>\\d+)\\}$");

    Part(String description) {
        this.description = parse(description);
    }

    private Map<Category, Integer> parse(String description) {
        Matcher matcher = PART_PATTERN.matcher(description);

        var result = matcher.matches();

        var x = Integer.parseInt(matcher.group("x"));
        var m = Integer.parseInt(matcher.group("m"));
        var a = Integer.parseInt(matcher.group("a"));
        var s = Integer.parseInt(matcher.group("s"));

        return Map.of(
                Category.X, x,
                Category.M, m,
                Category.A, a,
                Category.S, s
        );
    }

    public int get(Category category) {
        return description.get(category);
    }
}
