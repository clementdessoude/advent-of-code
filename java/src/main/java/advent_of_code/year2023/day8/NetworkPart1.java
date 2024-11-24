package advent_of_code.year2023.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NetworkPart1 {
    private final Map<String, String> rights;
    private final Map<String, String> lefts;

    public NetworkPart1(List<String> lines) {
        Map<String, String> rights = new HashMap<>();
        Map<String, String> lefts = new HashMap<>();

        for (String row : lines) {
            var split = row.split(" = ");
            var node = split[0];
            var directions = split[1]
                .replace("(", "")
                .replace(")", "")
                .split(", ");

            rights.put(node, directions[1]);
            lefts.put(node, directions[0]);
        }

        this.lefts = lefts;
        this.rights = rights;
    }

    public int navigate(char[] instructions) {
        String position = "AAA";
        int moves = 0;
        while (!position.equals("ZZZ")) {
            char instruction = instructions[moves % instructions.length];
            if (instruction == 'R') {
                position = rights.get(position);
            } else {
                position = lefts.get(position);
            }
            moves++;
        }

        return moves;
    }
}
