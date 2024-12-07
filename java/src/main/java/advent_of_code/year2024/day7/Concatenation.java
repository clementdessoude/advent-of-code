package advent_of_code.year2024.day7;

final class Concatenation implements Operator {

    private Concatenation() {}

    public static Concatenation CONCATENATION = new Concatenation();

    @Override
    public boolean couldApply(Long result, Long element) {
        var resultAsString = result.toString();
        var elementAsString = element.toString();

        return result > element && resultAsString.endsWith(elementAsString);
    }

    @Override
    public Long reducedResult(Long result, Long element) {
        var resultAsString = result.toString();
        var elementAsString = element.toString();

        var index = resultAsString.length() - elementAsString.length();
        return Long.parseLong(resultAsString.substring(0, index));
    }
}
