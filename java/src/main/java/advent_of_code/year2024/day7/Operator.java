package advent_of_code.year2024.day7;

interface Operator {
    boolean couldApply(Long result, Long element);
    Long reducedResult(Long result, Long element);
}
