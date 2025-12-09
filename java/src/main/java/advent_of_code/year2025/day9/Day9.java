package advent_of_code.year2025.day9;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

class Day9 {

    Long part1(List<String> lines) {
        var points = lines
            .stream()
            .map(line -> line.split(","))
            .map(split -> new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
            .toList();

        List<Rectangle> rectangles = new ArrayList<>();
        for (var point1 : points) {
            for (Point point2 : points) {
                rectangles.add(new Rectangle(point1, point2));
            }
        }

        return rectangles.stream().mapToLong(Rectangle::area).max().orElseThrow();
    }

    record Point(int x, int y) {}

    static final class Rectangle implements Comparable<Rectangle> {

        private final Point start;
        private final Point end;
        private final long area;

        Rectangle(Point start, Point end) {
            this.start = start;
            this.end = end;
            this.area = computeArea(start, end);
        }

        private static long computeArea(Point start, Point end) {
            return (
                ((long) Math.abs(end.x() - start.x()) + 1) *
                ((long) Math.abs(end.y() - start.y()) + 1)
            );
        }

        @Override
        public int compareTo(@NotNull Rectangle other) {
            return Comparator.comparingLong(Rectangle::area).compare(this, other);
        }

        public long area() {
            return area;
        }

        public Point start() {
            return start;
        }

        public Point end() {
            return end;
        }

        @Override
        public String toString() {
            return "Rectangle{" + "start=" + start + ", end=" + end + ", area=" + area + '}';
        }
    }

    Long part2(List<String> lines) {
        var points = lines
            .stream()
            .map(line -> line.split(","))
            .map(split -> new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
            .toList();

        List<Rectangle> rectangles = new ArrayList<>();
        for (var point1 : points) {
            for (Point point2 : points) {
                rectangles.add(new Rectangle(point1, point2));
            }
        }

        Map<Integer, List<Point>> borders = new HashMap<>();
        for (int i = 0; i < points.size(); i++) {
            var point = points.get(i);
            var next = points.get((i + 1) % points.size());

            var isVertical = point.x == next.x;
            if (isVertical) {
                var min = Math.min(point.y, next.y);
                var max = Math.max(point.y, next.y);
                for (int y = min; y <= max; y++) {
                    if (next.equals(new Point(point.x, y))) {
                        continue;
                    }
                    borders.putIfAbsent(y, new ArrayList<>());
                    borders.get(y).add(new Point(point.x, y));
                }
            } else {
                var min = Math.min(point.x, next.x);
                var max = Math.max(point.x, next.x);
                for (int x = min; x <= max; x++) {
                    if (next.equals(new Point(x, point.y))) {
                        continue;
                    }
                    borders.putIfAbsent(point.y, new ArrayList<>());
                    borders.get(point.y).add(new Point(x, point.y));
                }
            }
        }

        var sortedBorders = borders
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(Map.Entry::getKey, entry ->
                    entry.getValue().stream().sorted(Comparator.comparing(Point::x)).toList()
                )
            );

        int minY = sortedBorders
            .keySet()
            .stream()
            .min(Comparator.comparingInt(Integer::intValue))
            .orElseThrow();
        int maxY = sortedBorders
            .keySet()
            .stream()
            .max(Comparator.comparingInt(Integer::intValue))
            .orElseThrow();

        Map<Integer, List<Segment>> included = initIncludedSegments(sortedBorders, minY);
        for (int y = minY + 1; y <= maxY; y++) {
            var previous = included.get(y - 1);
            var lineSegments = concatenate(sortedBorders, y);
            List<Segment> includedLine = new ArrayList<>();
            for (int i = 0; i < lineSegments.size() - 1; i++) {
                var currentSegment = lineSegments.get(i);
                var nextSegment = lineSegments.get(i + 1);
                while (isInShapeTmp(previous, currentSegment)) {
                    currentSegment = new Segment(currentSegment.start, nextSegment.end);
                    i++;
                    if (i == lineSegments.size() - 1) {
                        break;
                    }
                    nextSegment = lineSegments.get(i + 1);
                }
                includedLine.add(currentSegment);
            }
            if (
                includedLine.isEmpty() ||
                !includedLine.getLast().end.equals(lineSegments.getLast().end)
            ) {
                includedLine.add(lineSegments.getLast());
            }
            included.put(y, includedLine);
        }

        System.out.println("Count: " + rectangles.size());
        var max = -1L;
        for (int i = 0; i < rectangles.size(); i++) {
            if (i % 1000 == 0) {
                System.out.println((double) i / rectangles.size());
            }
            var rectangle = rectangles.get(i);
            if (!isRectangleIncluded(rectangle, included)) {
                continue;
            }

            if (max < rectangle.area()) {
                max = rectangle.area();
            }
        }

        return max;
    }

    private static boolean isInShapeTmp(List<Segment> previous, Segment currentSegment) {
        return previous.stream().anyMatch(segment -> segment.include(currentSegment.end.x + 1));
    }

    private static Map<Integer, List<Segment>> initIncludedSegments(
        Map<Integer, List<Point>> borders,
        Integer minY
    ) {
        Map<Integer, List<Segment>> included = new HashMap<>();
        var minYSegment = concatenate(borders, minY);
        included.put(minY, minYSegment);
        return included;
    }

    private static List<Segment> concatenate(
        Map<Integer, List<Point>> borders,
        Integer lineNumber
    ) {
        var line = borders.get(lineNumber);
        var segments = new ArrayList<Segment>();
        for (int i = 0; i < line.size() - 1; i++) {
            var current = line.get(i);
            var currentSegment = new Segment(current, current);
            var next = line.get(i + 1);

            while (currentSegment.end.x == next.x - 1) {
                currentSegment = new Segment(currentSegment.start, next);
                i++;
                if (i == line.size() - 1) {
                    break;
                }
                next = line.get(i + 1);
            }
            segments.add(currentSegment);
        }
        if (!segments.getLast().end.equals(line.getLast())) {
            segments.add(new Segment(line.getLast(), line.getLast()));
        }
        return segments;
    }

    record Segment(Point start, Point end) {
        public boolean include(int i) {
            return start.x <= i && end.x >= i;
        }

        public boolean include(Segment segment) {
            return start.x <= segment.start.x && end.x >= segment.end.x;
        }
    }

    private boolean isRectangleIncluded(Rectangle rectangle, Map<Integer, List<Segment>> included) {
        var minX = Math.min(rectangle.start().x, rectangle.end().x);
        var maxX = Math.max(rectangle.start().x, rectangle.end().x);
        var minY = Math.min(rectangle.start().y, rectangle.end().y);
        var maxY = Math.max(rectangle.start().y, rectangle.end().y);

        for (var y = minY; y <= maxY; y++) {
            var includedLine = included.get(y);
            var segment = new Segment(new Point(minX, y), new Point(maxX, y));
            //            System.out.println(segment);
            if (!isIncluded(includedLine, segment)) {
                //                System.out.println("false");
                return false;
            }
        }

        return true;
    }

    private static boolean isIncluded(List<Segment> previous, Segment currentSegment) {
        return previous.stream().anyMatch(segment -> segment.include(currentSegment));
    }
}
