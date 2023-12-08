package adventOfCode.day8;

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

    // 13201
    // 18113
    // 22411
    // 20569
    // 21797
    // 24253
    public int navigate(char[] instructions) {
        List<String> positions = rights.keySet().stream().filter(s -> s.endsWith("A")).toList();

        int moves = 0;
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        while (!hasEnded(positions)) {
            if (flag) {
//                sb.append(positions.get(4)).append("-");
            }
            if (positions.get(5).endsWith("Z")) {
                if (flag) {
                    sb.append(positions.get(5));
                    break;
                } else {
                    System.out.println(moves);
                    System.out.println(moves % instructions.length);
                    sb.append(positions.get(5)).append("-");
                    flag = true;
                }
            }
            char instruction = instructions[moves % instructions.length];
            if (instruction == 'R') {
                positions = positions.stream().map(rights::get).toList();
            } else {
                positions = positions.stream().map(lefts::get).toList();
            }
            moves++;
        }

        System.out.println(moves);
        System.out.println(moves % instructions.length);
        System.out.println(sb.toString());
        return moves;
    }

    static boolean hasEnded(List<String> positions) {
        return positions.stream().filter(s -> s.endsWith("Z")).count() > 1;
    }
}
