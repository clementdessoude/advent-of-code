package advent_of_code.year2024.day7;

import advent_of_code.utils.StringUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

final class Equation {

    private final Long result;
    private final List<Long> parts;

    Equation(String input) {
        var split = input.split(":");
        result = Long.parseLong(split[0]);
        var partSplit = split[1].split(" ");
        parts = Arrays.stream(partSplit)
            .filter(StringUtils::isNotBlank)
            .map(Long::parseLong)
            .toList();
    }

    private Equation(Long result, List<Long> parts) {
        this.result = result;
        this.parts = parts;
    }

    boolean isPossiblyTrue(Operator... operators) {
        return isPossiblyTrue(Arrays.asList(operators));
    }

    boolean isPossiblyTrue(Collection<Operator> operators) {
        if (parts.size() == 1) {
            return result.equals(parts.getFirst());
        }
        return operators.stream().anyMatch(operator -> isPossiblyTrue(operator, operators));
    }

    private boolean isPossiblyTrue(Operator operator, Collection<Operator> operators) {
        var element = parts.getLast();

        if (!operator.couldApply(result, element)) {
            return false;
        }

        var reducedResult = operator.reducedResult(result, element);
        var reducedParts = parts.subList(0, parts.size() - 1);
        var reducedEquation = new Equation(reducedResult, reducedParts);

        return reducedEquation.isPossiblyTrue(operators);
    }

    public Long result() {
        return result;
    }
}
