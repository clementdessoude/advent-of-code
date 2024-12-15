package advent_of_code.year2022.day13;

public sealed interface Item permits CustomList, Value {
    int compare(Item other);
}
