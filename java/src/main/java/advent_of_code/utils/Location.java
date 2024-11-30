package advent_of_code.utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Location(int x, int y) {
    public static Stream<Location> stream(int minX, int minY, int maxX, int maxY) {
        return IntStream.range(minX, maxX)
            .mapToObj(x -> IntStream.range(minY, maxY).mapToObj(y -> new Location(x, y)))
            .flatMap(x -> x);
    }
}
