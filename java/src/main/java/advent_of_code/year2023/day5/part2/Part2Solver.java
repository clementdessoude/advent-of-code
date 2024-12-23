package advent_of_code.year2023.day5.part2;

import advent_of_code.year2023.day5.Range;
import java.util.*;

public class Part2Solver {

    private final List<Range> seeds;
    private final Mappings categoryMappings;

    public Part2Solver(List<String> lines) {
        this.seeds = parseSeeds(lines);
        this.categoryMappings = new Mappings(lines);
    }

    public Long solve() {
        Step step = new Step("seed", seeds, categoryMappings);
        while (step.hasNotFinished()) {
            step = step.next();
        }
        return step.minimum();
    }

    private static List<Range> parseSeeds(List<String> lines) {
        List<Range> seeds = new ArrayList<>();
        String[] seedStrings = lines.get(0).split(":")[1].split(" ");
        for (int i = 1; i < seedStrings.length; i += 2) {
            long start = Long.parseLong(seedStrings[i]);
            long range = Long.parseLong(seedStrings[i + 1]);
            seeds.add(new Range(start, start + range - 1));
        }
        return seeds;
    }
}
