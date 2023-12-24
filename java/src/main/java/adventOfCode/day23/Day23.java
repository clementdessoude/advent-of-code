
package adventOfCode.day23;

import static adventOfCode.Utils.csv;
import static adventOfCode.Utils.print;

import java.util.*;
import java.util.stream.Collectors;
import org.assertj.core.util.TriFunction;

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

