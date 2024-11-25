package advent_of_code.year2022.day4;

record Segment(int start, int end) {
    boolean contains(Segment other) {
        return end <= other.end && start >= other.start;
    }

    boolean containsOrIsContainedBy(Segment other) {
        return other.contains(this) || contains(other);
    }

    boolean overlaps(Segment other) {
        return (start >= other.start && start <= other.end) || (end >= other.start && end <= other.end) || containsOrIsContainedBy(other);
    }
}
