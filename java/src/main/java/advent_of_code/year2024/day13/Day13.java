package advent_of_code.year2024.day13;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

class Day13 {

    Long part1(List<String> lines) {
        List<ClawMachine> machines = parse(lines, true);
        return machines.stream().mapToLong(ClawMachine::resolve).sum();
    }

    Long part2(List<String> lines) {
        List<ClawMachine> machines = parse(lines, false);
        return machines.stream().mapToLong(ClawMachine::resolve).sum();
    }

    private static List<ClawMachine> parse(List<String> lines, boolean isPart1) {
        List<ClawMachine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 4) {
            machines.add(
                new ClawMachine(lines.get(i), lines.get(i + 1), lines.get(i + 2), isPart1)
            );
        }
        return machines;
    }
}
