package advent_of_code.year2023.day5;

public record Range(long start, long end) {

    public boolean overlaps(Range other) {
        return (this.start <= other.start && other.start <= this.end)
            || (other.start <= this.start && this.start <= other.end());
    }
}
