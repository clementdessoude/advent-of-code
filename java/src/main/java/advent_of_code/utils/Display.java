package advent_of_code.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Display {

    private Display() {}

    public static void display(List<List<String>> grid) {
        display(grid, "");
    }

    public static void display(List<List<String>> grid, String separator) {
        var result = gridWithIndex(grid);
        var str = String.join("\n", result.stream().map(r -> String.join(separator, r)).toList());
        System.out.println(str);
    }

    public static List<List<String>> gridWithIndex(List<List<String>> grid) {
        var gridWithIndex = new ArrayList<List<String>>();
        var list = IntStream.range(0, grid.getFirst().size())
            .mapToObj(i -> i + "")
            .collect(Collectors.toList());
        list.addFirst("-");
        gridWithIndex.add(list);
        for (int i = 0; i < grid.size(); i++) {
            var row = new ArrayList<String>(grid.get(i));
            row.addFirst(i + "");
            gridWithIndex.add(row);
        }
        return gridWithIndex;
    }
}
