
package advent_of_code.year2023.day16;

import java.util.*;
import java.util.stream.IntStream;

public class Day16 {

    public Long part1(List<String> lines) {
        return new Grid(lines).energizedCount(
            new Position(0, -1),
            BeamDirection.RIGHT
        );
    }

    public Long part2(List<String> lines) {
        int columnCount = lines.get(0).length();

        long maxRow = IntStream.range(0, lines.size())
                               .mapToLong(i -> {
                                   Long one = new Grid(lines).energizedCount(
                                       new Position(i, -1),
                                       BeamDirection.RIGHT
                                   );

                                   Long two = new Grid(lines).energizedCount(
                                       new Position(i, columnCount),
                                       BeamDirection.LEFT
                                   );

                                   return Math.max(one, two);
                               }).max().orElseThrow();

        long maxColumn = IntStream.range(0, columnCount)
                                  .mapToLong(j -> {
                                   Long one = new Grid(lines).energizedCount(
                                       new Position(-1, j),
                                       BeamDirection.DOWN
                                   );

                                   Long two = new Grid(lines).energizedCount(
                                       new Position(lines.size(), j),
                                       BeamDirection.UP
                                   );

                                   return Math.max(one, two);
                               }).max().orElseThrow();

        return Math.max(maxRow, maxColumn);
    }
}

