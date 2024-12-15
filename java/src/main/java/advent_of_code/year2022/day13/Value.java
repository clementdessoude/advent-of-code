package advent_of_code.year2022.day13;

import java.util.List;

public record Value(int value) implements Item {
    @Override
    public int compare(Item other) {
        return switch (other) {
            case Value v -> value - v.value;
            case CustomList list -> CustomList.compare(List.of(this), list.items());
        };
    }
}
