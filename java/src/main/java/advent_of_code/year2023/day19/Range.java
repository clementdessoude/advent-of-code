package advent_of_code.year2023.day19;

public record Range(int start, int end) {
    public Range above(Integer value) {
        if (start > value) {
            return this;
        }

        if (end <= value) {
            return null;
        }

        return new Range(value + 1, end);
    }

    public Range below(Integer value) {
        if (end < value) {
            return this;
        }

        if (start >= value) {
            return null;
        }

        return new Range(start, value - 1);
    }
}
