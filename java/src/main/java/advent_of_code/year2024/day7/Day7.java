package advent_of_code.year2024.day7;

import advent_of_code.utils.StringUtils;
import java.util.List;

class Day7 {

    Long part1(List<String> lines) {
        var equations = lines.stream().filter(StringUtils::isNotBlank).map(Equation::new).toList();

        return equations
            .stream()
            .filter(Equation::isPossiblyTrue)
            .mapToLong(Equation::result)
            .sum();
    }

    Long part2(List<String> lines) {
        return null;
    }
}
