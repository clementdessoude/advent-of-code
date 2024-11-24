package advent_of_code.year2022.day3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {

    public Long part1(List<String> lines) {
        return lines
            .stream()
            .map(line -> {
                int middle = line.length() / 2;
                return getDuplicate(line.substring(0, middle), line.substring(middle));
            })
            .mapToLong(this::priority)
            .sum();
    }

    char getDuplicate(String firstCompartment, String secondCompartment) {
        Set<Character> firstCompartmentChars = new HashSet<>();
        for (char c : firstCompartment.toCharArray()) {
            firstCompartmentChars.add(c);
        }
        for (char c : secondCompartment.toCharArray()) {
            if (firstCompartmentChars.contains(c)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No duplicate element found");
    }

    long priority(char c) {
        if (c >= 'a') {
            return (c - 'a') + 1L;
        }

        return (c - 'A') + 27L;
    }

    record Group(String elf1, String elf2, String elf3) {
        public char getBadge() {
            var duplicates = getDuplicates(elf1, elf2);
            for (char c : elf3.toCharArray()) {
                if (duplicates.contains(c)) {
                    return c;
                }
            }
            throw new IllegalArgumentException("No duplicate element found");
        }

        private Set<Character> getDuplicates(String one, String two) {
            Set<Character> duplicates = new HashSet<>();

            Set<Character> firstCompartmentChars = new HashSet<>();
            for (char c : one.toCharArray()) {
                firstCompartmentChars.add(c);
            }
            for (char c : two.toCharArray()) {
                if (firstCompartmentChars.contains(c)) {
                    duplicates.add(c);
                }
            }

            return duplicates;
        }
    }

    public Long part2(List<String> lines) {
        return toGroups(lines).stream().map(Group::getBadge).mapToLong(this::priority).sum();
    }

    private List<Group> toGroups(List<String> lines) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            groups.add(new Group(lines.get(i), lines.get(i + 1), lines.get(i + 2)));
        }
        return groups;
    }
}
