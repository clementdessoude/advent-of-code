package adventOfCode.day8;

import java.util.List;

public class Day8 {
    public int part1(List<String> lines) {
        String instructions = lines.get(0);
        NetworkPart1 network = new NetworkPart1(lines.subList(2, lines.size()));

        return network.navigate(instructions.toCharArray());
    }

    public int part2(List<String> lines) {
        String instructions = lines.get(0);
        NetworkPart2 network = new NetworkPart2(lines.subList(2, lines.size()));

        return network.navigate(instructions.toCharArray());
    }
}
