package advent_of_code.year2024.day7;

import static advent_of_code.year2024.day7.Addition.*;
import static advent_of_code.year2024.day7.Concatenation.*;
import static advent_of_code.year2024.day7.Multiplication.*;

import advent_of_code.utils.StringUtils;
import java.util.List;

class Day7 {

    Long part1(List<String> lines) {
        return lines
            .stream()
            .filter(StringUtils::isNotBlank)
            .map(Equation::new)
            .filter(equation -> equation.isPossiblyTrue(MULTIPLICATION, ADDITION))
            .mapToLong(Equation::result)
            .sum();
    }

    Long part2(List<String> lines) {
        return lines
            .stream()
            .filter(StringUtils::isNotBlank)
            .map(Equation::new)
            .filter(equation -> equation.isPossiblyTrue(MULTIPLICATION, ADDITION, CONCATENATION))
            .mapToLong(Equation::result)
            .sum();
    }
}
