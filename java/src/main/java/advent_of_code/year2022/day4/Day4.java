package advent_of_code.year2022.day4;

import static advent_of_code.Utils.COMMA_SEPARATOR;

import advent_of_code.utils.Pair;
import java.util.List;
import java.util.stream.Stream;

public class Day4 {

    public Long part1(List<String> lines) {
        var pairs = parse(lines);
        return pairs.filter(pair -> pair.first().containsOrIsContainedBy(pair.second())).count();
    }

    public Long part2(List<String> lines) {
        return parse(lines).filter(pair -> pair.first().overlaps(pair.second())).count();
    }

    private static Stream<Pair<Segment, Segment>> parse(List<String> lines) {
        return lines
            .stream()
            .map(COMMA_SEPARATOR)
            .map(split -> {
                String[] segmentOne = split[0].split("-");
                String[] segmentTwo = split[1].split("-");

                return new Pair<>(
                    new Segment(Integer.parseInt(segmentOne[0]), Integer.parseInt(segmentOne[1])),
                    new Segment(Integer.parseInt(segmentTwo[0]), Integer.parseInt(segmentTwo[1]))
                );
            });
    }
}
