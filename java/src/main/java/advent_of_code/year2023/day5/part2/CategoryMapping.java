package advent_of_code.year2023.day5.part2;

import java.util.List;

record CategoryMapping(
    CategoryDescription description,
    List<Mapping> mappings
) {

    static CategoryMapping from(List<String> categoryLines) {
        var description = new CategoryDescription(categoryLines.get(0));
        var mappings = Mapping.mappings(categoryLines.subList(
            1,
            categoryLines.size()
        ));

        return new CategoryMapping(description, mappings);
    }
}
