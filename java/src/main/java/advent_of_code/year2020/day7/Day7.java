package advent_of_code.year2020.day7;

import static java.lang.Integer.parseInt;

import advent_of_code.year2023.day2.Day2;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day7 {

    private static final Pattern BAG_PATTERN = Pattern.compile(
        "(?<color>\\w+ \\w+) bags contain (?<bags>(?:(?<bagCount>\\d+) (?<bagColor>\\w+ \\w+) bags?[,.] ?)+|(no other bags.))"
    );

    private static final Pattern BAG_SUB_PATTERN = Pattern.compile(
        "(?<bagCount>\\d+) (?<bagColor>\\w+ \\w+) bags?[,.] ?"
    );

    Long part1(List<String> lines) {
        var bags = parse(lines);

        return bags.values().stream().filter(bag -> bag.contains(new Bag("shiny gold"))).count();
    }

    private Map<String, Bag> parse(List<String> lines) {
        Map<String, Bag> bags = new HashMap<>();
        for (String line : lines) {
            Matcher matcher = BAG_PATTERN.matcher(line);
            if (!matcher.matches()) {
                System.out.println(line);
                continue;
            }
            var color = matcher.group("color");

            bags.putIfAbsent(color, new Bag(color));
            var bag = bags.get(color);
            var bagsMatcher = BAG_SUB_PATTERN.matcher(matcher.group("bags"));

            while (bagsMatcher.find()) {
                var bagCount = parseInt(bagsMatcher.group("bagCount"));
                var bagColor = bagsMatcher.group("bagColor");

                bags.putIfAbsent(bagColor, new Bag(bagColor));
                var subBag = bags.get(bagColor);
                bag.add(subBag, bagCount);
            }
        }

        return bags;
    }

    Long part2(List<String> lines) {
        var bags = parse(lines);
        var shinyGoldBag = bags.get("shiny gold");
        return shinyGoldBag.bagSize();
    }
}
