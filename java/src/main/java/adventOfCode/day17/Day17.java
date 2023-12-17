
package adventOfCode.day17;

import java.util.List;
import java.util.stream.IntStream;

public class Day17 {

    public Long part1(List<String> lines) {
        List<List<Position>> positions = IntStream
            .range(0, lines.size())
            .mapToObj(i -> IntStream
                .range(0, lines.get(0).length())
                .mapToObj(j -> new Position(
                    i,
                    j,
                    Integer.parseInt(String.valueOf(lines.get(i).charAt(j))))
                )
                .toList()
            )
            .toList();

        Djikstra djikstra = new Djikstra(positions);
        djikstra.calculateShortestPathFromSource();

        var destination = positions.getLast().getLast();

        djikstra.displayPath(positions.get(1).get(6));

        return djikstra.lowest.get(new Location(destination.i(), destination.j())).values().stream()
            .flatMapToLong(s -> s.values().stream().mapToLong(l -> l))
            .min()
            .orElseThrow();
    }

    public Long part2(List<String> lines) {
        return null;
    }
}

