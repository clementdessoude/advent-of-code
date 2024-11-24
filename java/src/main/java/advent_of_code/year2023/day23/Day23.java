
package advent_of_code.year2023.day23;

import java.util.*;

public class Day23 {

    public int part1(List<String> lines) {
        return 0;
    }

    public int part2(List<String> lines) {
        Graph graph = Graph.from(lines);

        return graph.longestPathTo(new Location(
            lines.size() - 1,
            lines.size() - 2
        ));
    }
}

