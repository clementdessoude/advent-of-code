package advent_of_code.year2023.day22;

record Range(int start, int end) {

    Range(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    boolean intersects(Range range) {
        if (start <= range.start) {
            return end >= range.start;
        }

        return start <= range.end;
    }

    public int length() {
        return end - start + 1;
    }
}
