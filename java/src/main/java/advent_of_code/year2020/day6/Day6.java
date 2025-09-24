package advent_of_code.year2020.day6;

import java.util.ArrayList;
import java.util.List;

class Day6 {

    Long part1(List<String> lines) {
        var groups = getGroups(lines);

        return groups.stream().mapToLong(Group::getDistinctAnswers).sum();
    }

    private List<Group> getGroups(List<String> lines) {
        var groups = new ArrayList<Group>();

        var group = new Group();
        for (String line : lines) {
            if (line.isBlank()) {
                groups.add(group);
                group = new Group();
            } else {
                group.add(line);
            }
        }

        groups.add(group);
        return groups;
    }

    Long part2(List<String> lines) {
        var groups = getGroups(lines);

        return groups.stream().mapToLong(Group::getCommonAnswers).sum();
    }
}
