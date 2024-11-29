package advent_of_code.year2022.day8;

import advent_of_code.utils.Location;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Forest {

    private final List<List<Integer>> trees;

    Forest(List<List<Integer>> trees) {
        this.trees = trees;
    }

    Collection<Location> visibleTrees() {
        Set<Location> visibleTreesInRows = IntStream.range(0, trees.size())
            .mapToObj(this::visibleTreesInRow)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        Set<Location> visibleTreesInColumns = IntStream.range(0, trees.getFirst().size())
            .mapToObj(this::visibleTreesInColumn)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        var result = new HashSet<Location>();
        result.addAll(visibleTreesInRows);
        result.addAll(visibleTreesInColumns);

        return result;
    }

    public Collection<Location> visibleTreesInRow(int rowIndex) {
        List<Integer> row = trees.get(rowIndex);
        return visibleTrees(row, i -> new Location(i, rowIndex));
    }

    public Collection<Location> visibleTreesInColumn(int columnIndex) {
        List<Integer> column = trees.stream().map(row -> row.get(columnIndex)).toList();
        return visibleTrees(column, i -> new Location(columnIndex, i));
    }

    public Collection<Location> visibleTrees(
        List<Integer> ints,
        IntFunction<Location> locationProvider
    ) {
        int size = ints.size();

        List<Integer> maxHeightsToTheLeft = new ArrayList<>();
        List<Integer> maxHeightsToTheRight = new ArrayList<>();
        maxHeightsToTheLeft.add(-1);
        maxHeightsToTheRight.add(-1);

        for (int i = 0; i < size; i++) {
            maxHeightsToTheLeft.add(Math.max(maxHeightsToTheLeft.getLast(), ints.get(i)));
            maxHeightsToTheRight.addFirst(
                Math.max(maxHeightsToTheRight.getFirst(), ints.get(size - i - 1))
            );
        }

        List<Location> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (
                maxHeightsToTheLeft.get(i) < ints.get(i) ||
                maxHeightsToTheRight.get(i + 1) < ints.get(i)
            ) {
                result.add(locationProvider.apply(i));
            }
        }

        return result;
    }
}
