package advent_of_code.year2022.day13;

import java.util.List;

record CustomList(List<Item> items) implements Item {
    @Override
    public int compare(Item other) {
        return switch (other) {
            case CustomList list -> compare(items, list.items);
            case Value value -> compare(items, List.of(value));
        };
    }

    static int compare(List<Item> items, List<Item> list) {
        for (int i = 0; i < items.size(); i++) {
            if (list.size() <= i) {
                return 1;
            }
            var compareItem = items.get(i).compare(list.get(i));
            if (compareItem != 0) {
                return compareItem;
            }
        }
        if (items.size() < list.size()) {
            return -1;
        }
        return 0;
    }
}
