package advent_of_code.year2025.day8;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

class Day8 {

    Long part1(List<String> lines, int iterationsCount) {
        var points = lines
            .stream()
            .map(line -> line.split(","))
            .map(split ->
                new Point(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
                )
            )
            .toList();

        var distances = distances(lines, points);

        List<Set<Point>> networks = new ArrayList<>();
        for (int i = 0; i < iterationsCount; i++) {
            var segment = distances.getFirst().getFirst();

            var matchingStartAndEndNetwork = networks
                .stream()
                .anyMatch(
                    network -> network.contains(segment.start) && network.contains(segment.end)
                );

            if (matchingStartAndEndNetwork) {
                distances.getFirst().removeFirst();
                distances = sortDistances(distances);
                continue;
            }

            var matchingStartNetwork = networks
                .stream()
                .filter(network -> network.contains(segment.start))
                .findFirst();

            var matchingEndNetwork = networks
                .stream()
                .filter(network -> network.contains(segment.end))
                .findFirst();

            if (matchingStartNetwork.isEmpty() && matchingEndNetwork.isEmpty()) {
                Set<Point> newNetwork = new HashSet<>();
                newNetwork.add(segment.start);
                newNetwork.add(segment.end);
                networks.add(newNetwork);
            } else if (matchingStartNetwork.isPresent() && matchingEndNetwork.isPresent()) {
                matchingStartNetwork.get().addAll(matchingEndNetwork.get());
                networks.remove(matchingEndNetwork.get());
            } else if (matchingStartNetwork.isPresent()) {
                matchingStartNetwork.get().add(segment.end);
            } else {
                matchingEndNetwork.get().add(segment.start);
            }

            distances.getFirst().removeFirst();
            distances = sortDistances(distances);
        }

        var sortedNetworks = networks
            .stream()
            .sorted(Comparator.comparing(Set::size))
            .toList()
            .reversed();

        return (
            (long) sortedNetworks.getFirst().size() *
            sortedNetworks.get(1).size() *
            sortedNetworks.get(2).size()
        );
    }

    private static List<List<Segment>> distances(List<String> lines, List<Point> points) {
        List<List<Segment>> distances = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var point = points.get(i);
            List<Segment> segments = new ArrayList<>();
            for (int j = i + 1; j < lines.size(); j++) {
                segments.add(new Segment(point, points.get(j)));
            }
            distances.add(segments.stream().sorted().collect(Collectors.toList()));
        }

        return sortDistances(distances);
    }

    private static @NotNull List<List<Segment>> sortDistances(List<List<Segment>> distances) {
        return distances
            .stream()
            .filter(segs -> !segs.isEmpty())
            .sorted(Comparator.comparing(list -> list.getFirst().distance))
            .collect(Collectors.toList());
    }

    Long part2(List<String> lines) {
        return null;
    }

    record Point(int x, int y, int z) {
        double distanceTo(Point point) {
            return Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2) + Math.pow(z - point.z, 2);
        }
    }

    static final class Segment implements Comparable<Segment> {

        private final Point start;
        private final Point end;
        private final double distance;

        Segment(Point start, Point end) {
            this.start = start;
            this.end = end;
            this.distance = start.distanceTo(end);
        }

        public double distance() {
            return distance;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Segment) obj;
            return (
                Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end) &&
                Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(that.distance)
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, distance);
        }

        @Override
        public String toString() {
            return (
                "Segment[" +
                "start=" +
                start +
                ", " +
                "end=" +
                end +
                ", " +
                "distance=" +
                distance +
                ']'
            );
        }

        @Override
        public int compareTo(@NotNull Segment other) {
            return Comparator.comparing(Segment::distance).compare(this, other);
        }
    }
}
