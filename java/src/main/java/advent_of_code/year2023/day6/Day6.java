package advent_of_code.year2023.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public Long part1(List<String> lines) {
        var races = parse(lines.get(0), lines.get(1));

        return races
            .stream()
            .mapToLong(Race::possibleWinningOptions)
            .reduce(1, (a, b) -> a * b);
    }

    record Race(long time, long distance) {

        long possibleWinningOptions() {
            var discriminant = Math.pow(time, 2) - 4 * distance;
            if (discriminant < 0) {
                return 0;
            }
            double firstRoot = (time - Math.sqrt(discriminant)) / 2.0;
            double secondRoot = (time + Math.sqrt(discriminant)) / 2.0;

            long firstPossibleOption = (long) Math.floor(Math.max(firstRoot, 0)) + 1;
            long lastPossibleOption = (long) Math.ceil(Math.max(secondRoot, 0)) - 1;


            return lastPossibleOption - firstPossibleOption + 1;
        }
    }

    private List<Race> parse(String timesString, String distancesString) {
        var times = Arrays
            .stream(timesString.split("\\s+"))
            .skip(1)
            .map(Long::parseLong)
            .toList();

        var distances = Arrays
            .stream(distancesString.split("\\s+"))
            .skip(1)
            .map(Long::parseLong)
            .toList();

        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }

        return races;
    }

    public Long part2(List<String> lines) {
        Race race = parsePart2(lines.get(0), lines.get(1));
        return race.possibleWinningOptions();
    }

    private Race parsePart2(String timesString, String distancesString) {
        long time = Long.parseLong(timesString.replace(" ", "").split(":")[1]);
        long distance = Long.parseLong(distancesString.replace(" ", "").split(":")[1]);

        return new Race(time, distance);
    }
}
