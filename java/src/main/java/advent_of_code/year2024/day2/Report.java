package advent_of_code.year2024.day2;

import java.util.List;

final class Report {

    private final List<Integer> levels;

    public Report(List<Integer> levels) {
        this.levels = levels;
    }

    boolean isSafe() {
        int shouldGloballyIncreaseIncrease = levels.getFirst() < levels.get(1) ? 1 : -1;

        for (int i = 1; i < levels.size(); i++) {
            int increasing = levels.get(i - 1) < levels.get(i) ? 1 : -1;
            if (increasing * shouldGloballyIncreaseIncrease == -1) {
                return false;
            }

            int difference = levels.get(i) - levels.get(i - 1);
            if (Math.abs(difference) == 0 || Math.abs(difference) > 3) {
                return false;
            }
        }
        return true;
    }
}
