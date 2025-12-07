package advent_of_code.year2025.day7;

import advent_of_code.utils.Location;
import java.util.*;

class Day7 {

    Long part1(List<String> lines) {
        var grid = new Grid(lines);

        return grid.numberOfSplits();
    }

    static class Grid {

        private final Map<Location, Character> map;
        private final int size;
        private final int width;
        private final Location source;

        Grid(List<String> lines) {
            map = parse(lines);
            size = lines.size();
            width = lines.getFirst().length();
            source = new Location(lines.getFirst().indexOf("S"), 0);
        }

        private Map<Location, Character> parse(List<String> lines) {
            Map<Location, Character> map = new HashMap<>();
            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    var location = new Location(x, y);
                    map.put(location, line.charAt(x));
                }
            }

            return map;
        }

        public Long numberOfSplits() {
            Set<Location> beams = new HashSet<>();
            beams.add(source);

            var count = 0L;
            for (int i = 0; i < size - 2; i++) {
                Set<Location> nextBeams = new HashSet<>();
                for (Location location : beams) {
                    var down = location.down();
                    if (map.get(down) == '^') {
                        count++;
                        var left = down.left();
                        if (left.isInGrid(0, 0, width, size)) {
                            nextBeams.add(left);
                        }
                        var right = down.right();
                        if (right.isInGrid(0, 0, width, size)) {
                            nextBeams.add(right);
                        }
                    } else {
                        nextBeams.add(down);
                    }
                }

                beams = nextBeams;
            }

            return count;
        }

        public Long timelines() {
            Set<Location> beams = new HashSet<>();
            Map<Location, Long> timelines = new HashMap<>();
            beams.add(source);
            timelines.put(source, 1L);

            for (int i = 0; i < size - 2; i++) {
                Set<Location> nextBeams = new HashSet<>();
                for (Location location : beams) {
                    var down = location.down();
                    var actual = timelines.get(location);
                    if (map.get(down) == '^') {
                        var left = down.left();
                        if (left.isInGrid(0, 0, width, size)) {
                            nextBeams.add(left);
                            timelines.put(left, timelines.getOrDefault(left, 0L) + actual);
                        }
                        var right = down.right();
                        if (right.isInGrid(0, 0, width, size)) {
                            nextBeams.add(right);
                            timelines.put(right, timelines.getOrDefault(right, 0L) + actual);
                        }
                    } else {
                        nextBeams.add(down);
                        timelines.put(down, timelines.getOrDefault(down, 0L) + actual);
                    }
                }
                beams = nextBeams;
            }

            return beams.stream().mapToLong(timelines::get).sum();
        }
    }

    Long part2(List<String> lines) {
        var grid = new Grid(lines);

        return grid.timelines();
    }
}
