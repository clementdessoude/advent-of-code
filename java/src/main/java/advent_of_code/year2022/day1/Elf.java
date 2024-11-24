package advent_of_code.year2022.day1;

import java.util.ArrayList;
import java.util.List;

record Elf(List<Integer> calories) {
    Elf() {
        this(new ArrayList<>());
    }

    long totalCalories() {
        return calories.stream().mapToInt(i -> i).sum();
    }

    void addCalories(int calories) {
        this.calories.add(calories);
    }
}
