package advent_of_code.year2025.day5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Day5 {

    Long part1(List<String> lines) {
        return input(lines).freshValues();
    }

    record Range(Long lower, Long upper) {
        public boolean includes(Long value) {
            return lower <= value && value <= upper;
        }

        public long size() {
            if (lower > upper) {
                return 0;
            }

            return upper - lower + 1;
        }
    }

    record Input(List<Range> ranges, List<Long> values) {
        Long freshValues() {
            return values
                .stream()
                .filter(value -> ranges.stream().anyMatch(range -> range.includes(value)))
                .count();
        }

        public Long freshOptions() {
            List<Range> rangesWithoutOverlaps = new ArrayList<>();

            var sortedRanges = ranges
                .stream()
                .sorted(Comparator.comparing(Range::upper).reversed().thenComparing(Range::lower))
                .toList();

            rangesWithoutOverlaps.add(sortedRanges.getFirst());

            for (int i = 1; i < sortedRanges.size(); i++) {
                var range = sortedRanges.get(i);
                var lastRange = rangesWithoutOverlaps.getFirst();

                if (lastRange.lower.equals(range.lower)) {
                    continue;
                }

                var newMax = Math.min(range.upper(), lastRange.lower());
                if (newMax == lastRange.lower()) {
                    newMax -= 1;
                }
                if (newMax < range.lower()) {
                    continue;
                }
                rangesWithoutOverlaps.addFirst(new Range(range.lower(), newMax));
            }

            System.out.println(rangesWithoutOverlaps);
            return rangesWithoutOverlaps.stream().mapToLong(Range::size).sum();
        }
    }

    Input input(List<String> lines) {
        List<Range> ranges = new ArrayList<>();
        var start = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                start = i + 1;
                break;
            }

            String[] split = line.split("-");
            long lower = Long.parseLong(split[0]);
            long upper = Long.parseLong(split[1]);
            ranges.add(new Range(lower, upper));
        }

        List<Long> values = lines
            .subList(start, lines.size())
            .stream()
            .map(Long::parseLong)
            .toList();

        return new Input(ranges, values);
    }

    Long part2(List<String> lines) {
        return input(lines).freshOptions();
    }
}
