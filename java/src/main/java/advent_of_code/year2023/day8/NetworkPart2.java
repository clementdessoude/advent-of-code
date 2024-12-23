package advent_of_code.year2023.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NetworkPart2 {

    private final Map<String, String> rights;
    private final Map<String, String> lefts;

    public NetworkPart2(List<String> lines) {
        Map<String, String> rights = new HashMap<>();
        Map<String, String> lefts = new HashMap<>();

        for (String row : lines) {
            var split = row.split(" = ");
            var node = split[0];
            var directions = split[1].replace("(", "").replace(")", "").split(", ");

            rights.put(node, directions[1]);
            lefts.put(node, directions[0]);
        }

        this.lefts = lefts;
        this.rights = rights;
    }

    public long navigate(char[] instructions) {
        List<String> positions = rights.keySet().stream().filter(s -> s.endsWith("A")).toList();

        List<Long> cycles = positions
            .stream()
            .map(initialPosition -> getCycle(initialPosition, instructions))
            .toList();

        return Utils.lcm(cycles.toArray(new Long[0]));
    }

    private Long getCycle(String initialPosition, char[] instructions) {
        String position = initialPosition;
        long moves = 0L;
        while (!position.endsWith("Z")) {
            char instruction = instructions[(int) (moves % instructions.length)];
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
