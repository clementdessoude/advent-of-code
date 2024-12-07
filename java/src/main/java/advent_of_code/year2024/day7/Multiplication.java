package advent_of_code.year2024.day7;

final class Multiplication implements Operator {

    private Multiplication() {}

    public static Multiplication MULTIPLICATION = new Multiplication();

    @Override
    public boolean couldApply(Long result, Long element) {
        return result % element == 0;
    }

    @Override
    public Long reducedResult(Long result, Long element) {
        return result / element;
    }
}
