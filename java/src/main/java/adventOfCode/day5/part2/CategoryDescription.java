package adventOfCode.day5.part2;

import java.util.regex.Pattern;

final class CategoryDescription {
    private final String source;
    private final String destination;
    private static final Pattern CATEGORY_DESCRIPTION_PATTERN = Pattern.compile("(?<source>([a-z]*))-to-(?<destination>([a-z]*)) map:");

    CategoryDescription(String description) {
        var matcher = CATEGORY_DESCRIPTION_PATTERN.matcher(description);
        matcher.matches();

        this.source = matcher.group("source");
        this.destination = matcher.group("destination");
    }

    public String source() {
        return source;
    }

    public String destination() {
        return destination;
    }
}
