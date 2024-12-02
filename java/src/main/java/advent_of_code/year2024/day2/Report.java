package advent_of_code.year2024.day2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

final class Report {

    private final List<Integer> levels;

    public Report(List<Integer> levels) {
        this.levels = levels;
    }

    boolean isSafe() {
        int shouldGloballyIncreaseIncrease = levels.getFirst() < levels.get(1) ? 1 : -1;

        for (int i = 1; i < levels.size(); i++) {
            if (isNotSafe(shouldGloballyIncreaseIncrease, i)) {
                return false;
            }
        }
        return true;
    }

    boolean isSafeWithDampenerProblem() {
        int shouldGloballyIncrease = shouldGloballyIncrease();
        var indexesToRemove = getPossibleIndexesToRemove(shouldGloballyIncrease);

        if (indexesToRemove.size() > 3) {
            return false;
        }

        if (indexesToRemove.isEmpty()) {
            return true;
        }

        var possibleReports = indexesToRemove
            .stream()
            .map(this::reportWithoutLevelAtIndex)
            .toList();

        var safeReport = possibleReports.stream().filter(Report::isSafe).findFirst();
        return safeReport.isPresent();
    }

    private Report reportWithoutLevelAtIndex(Integer indexToRemove) {
        var newLevels = IntStream.range(0, levels.size())
            .filter(i -> i != indexToRemove)
            .map(levels::get)
            .boxed()
            .toList();

        return new Report(newLevels);
    }

    private Set<Integer> getPossibleIndexesToRemove(int shouldGloballyIncrease) {
        Set<Integer> indexesToRemove = new HashSet<>();
        for (int i = 1; i < levels.size(); i++) {
            if (isNotSafe(shouldGloballyIncrease, i)) {
                indexesToRemove.add(i);
                indexesToRemove.add(i - 1);
            }
        }
        return indexesToRemove;
    }

    private int shouldGloballyIncrease() {
        int shouldGloballyIncreaseIncrease1 = levels.getFirst() < levels.get(1) ? 1 : -1;
        int shouldGloballyIncreaseIncrease2 = levels.get(1) < levels.get(2) ? 1 : -1;
        int shouldGloballyIncreaseIncrease3 = levels.get(2) < levels.get(3) ? 1 : -1;

        int shouldGloballyIncreaseIncrease;
        if (shouldGloballyIncreaseIncrease1 * shouldGloballyIncreaseIncrease2 == 1) {
            shouldGloballyIncreaseIncrease = shouldGloballyIncreaseIncrease1;
        } else if (shouldGloballyIncreaseIncrease1 * shouldGloballyIncreaseIncrease3 == 1) {
            shouldGloballyIncreaseIncrease = shouldGloballyIncreaseIncrease1;
        } else {
            shouldGloballyIncreaseIncrease = shouldGloballyIncreaseIncrease2;
        }
        return shouldGloballyIncreaseIncrease;
    }

    private boolean isNotSafe(int shouldGloballyIncrease, int index) {
        return (
            isNotCoherentWithGlobalIncrease(shouldGloballyIncrease, index) ||
            isNotInSafeRange(index)
        );
    }

    private boolean isNotCoherentWithGlobalIncrease(int shouldGloballyIncrease, int index) {
        int increasing = levels.get(index - 1) < levels.get(index) ? 1 : -1;
        return increasing * shouldGloballyIncrease != 1;
    }

    private boolean isNotInSafeRange(int index) {
        int difference = levels.get(index) - levels.get(index - 1);
        return Math.abs(difference) == 0 || Math.abs(difference) > 3;
    }
}
