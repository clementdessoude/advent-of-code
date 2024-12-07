package advent_of_code.year2024.day7;

final class Addition implements Operator {

    private Addition() {}

    public static Addition ADDITION = new Addition();

    @Override
    public boolean couldApply(Long result, Long element) {
        return element <= result;
    }

    @Override
    public Long reducedResult(Long result, Long element) {
        return result - element;
    }
}
