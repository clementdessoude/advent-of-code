
package adventOfCode.day24;

import java.util.List;

class Day24 {

    int part1(List<String> lines, Area area) {
        List<Hail> hails = lines.stream().map(Day24::parse).toList();

        int count = 0;
        for (int i = 0; i < hails.size(); i++) {
            for (int j = i + 1; j < hails.size(); j++) {
                var first = hails.get(i);
                var second = hails.get(j);

                if (first.willIntersect(second, area)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static Hail parse(String row) {
        var split = row.split(" @ ");
        var position = split[0].split(", ");
        var velocity = split[1].split(", ");

        return new Hail(
            Double.parseDouble(position[0].replace(" ", "")),
            Double.parseDouble(position[1].replace(" ", "")),
            Double.parseDouble(position[2].replace(" ", "")),
            Double.parseDouble(velocity[0].replace(" ", "")),
            Double.parseDouble(velocity[1].replace(" ", "")),
            Double.parseDouble(velocity[2].replace(" ", ""))
        );
    }

    Long part2(List<String> lines) {
        return null;
    }
}

