package advent_of_code.year2022.day1;

import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public Long part1(List<String> lines) {
        var elves = parse(lines);
        return elves.stream().mapToLong(Elf::totalCalories).max().orElse(0L);
    }

    public Long part2(List<String> lines) {
        var elves = parse(lines);

        return elves
            .stream()
            .map(Elf::totalCalories)
            .sorted((a, b) -> Long.compare(b, a))
            .limit(3)
            .mapToLong(Long::longValue)
            .sum();
    }

    private static List<Elf> parse(List<String> lines) {
        List<Elf> elves = new ArrayList<>();
        var elf = new Elf();
        for (String line : lines) {
            if (line.isBlank()) {
                elves.add(elf);
                elf = new Elf();
            } else {
                elf.addCalories(Integer.parseInt(line));
            }
        }
        elves.add(elf);

        return elves;
    }
}
