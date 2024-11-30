package advent_of_code.year2022.day8;

import advent_of_code.utils.Location;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

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

    int maxScenicView() {
        var viewingDistancesLefts = IntStream.range(0, trees.size())
            .mapToObj(this::viewingDistancesLeft)
            .toList();

        var viewingDistancesRights = IntStream.range(0, trees.size())
            .mapToObj(this::viewingDistancesRight)
            .toList();

        var viewingDistancesTops = IntStream.range(0, trees.getFirst().size())
            .mapToObj(this::viewingDistancesTop)
            .toList();

        var viewingDistancesBottoms = IntStream.range(0, trees.getFirst().size())
            .mapToObj(this::viewingDistancesBottom)
            .toList();

        return Location.stream(0, 0, trees.getFirst().size(), trees.size())
            .mapToInt(location -> {
                var x = location.x();
                var y = location.y();

                return (
                    viewingDistancesLefts.get(y).get(x) *
                    viewingDistancesRights.get(y).get(x) *
                    viewingDistancesTops.get(x).get(y) *
                    viewingDistancesBottoms.get(x).get(y)
                );
            })
            .max()
            .orElse(0);
    }

    private List<Integer> viewingDistancesLeft(int rowIndex) {
        var row = trees.get(rowIndex);

        return getAscending(row, i -> new Location(i, rowIndex), true);
    }

    private List<Integer> viewingDistancesTop(int columnIndex) {
        var column = trees.stream().map(tree -> tree.get(columnIndex)).toList();
        return getAscending(column, i -> new Location(columnIndex, i), false);
    }

    private List<Integer> getAscending(
        List<Integer> row,
        IntFunction<Location> locationProvider,
        boolean isRow
    ) {
        List<Map<Integer, Integer>> distanceToTreeOfSize = new ArrayList<>();
        HashMap<Integer, Integer> first = new HashMap<>();
        first.put(9, 0);
        distanceToTreeOfSize.add(first);
        //        System.out.println(first);
        for (int i = 1; i < row.size(); i++) {
            var previous = distanceToTreeOfSize.get(i - 1);

            var newDistance = previous
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() + 1));

            var current = row.get(i - 1);
            newDistance.compute(current, (k, v) -> 1);
            distanceToTreeOfSize.add(newDistance);
            //            System.out.println(newDistance);
        }

        return IntStream.range(0, row.size())
            .mapToObj(i -> {
                var location = locationProvider.apply(i);
                return getViewingDistance(location.x(), location.y(), isRow, distanceToTreeOfSize);
            })
            .toList();
    }

    private List<Integer> viewingDistancesRight(int rowIndex) {
        var row = trees.get(rowIndex);
        return getDescending(row, i -> new Location(i, rowIndex), true);
    }

    private List<Integer> viewingDistancesBottom(int columnIndex) {
        var column = trees.stream().map(tree -> tree.get(columnIndex)).toList();
        return getDescending(column, i -> new Location(columnIndex, i), false);
    }

    private List<Integer> getDescending(
        List<Integer> elements,
        IntFunction<Location> locationProvider,
        boolean isRow
    ) {
        List<Map<Integer, Integer>> distanceToTreeOfSize = new ArrayList<>();
        HashMap<Integer, Integer> first = new HashMap<>();
        first.put(9, 0);
        distanceToTreeOfSize.add(first);
        for (int i = elements.size() - 2; i >= 0; i--) {
            var previous = distanceToTreeOfSize.getFirst();

            var newDistance = previous
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() + 1));

            var current = elements.get(i + 1);
            newDistance.compute(current, (k, v) -> 1);
            distanceToTreeOfSize.addFirst(newDistance);
        }

        return IntStream.range(0, elements.size())
            .mapToObj(i -> {
                var location = locationProvider.apply(i);
                return getViewingDistance(location.x(), location.y(), isRow, distanceToTreeOfSize);
            })
            .toList();
    }

    private int getViewingDistance(
        int x,
        int y,
        boolean row,
        List<Map<Integer, Integer>> distanceToTreeOfSize
    ) {
        var treeSize = trees.get(y).get(x);

        var value = distanceToTreeOfSize
            .get(row ? x : y)
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey() >= treeSize)
            .min(Map.Entry.comparingByValue())
            .orElseThrow();

        return value.getValue();
    }
}
