package advent_of_code.year2024.day12;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Region {

    private final Set<Location> locations = new HashSet<>();

    public Region() {}

    public Region(Location[] provided) {
        locations.addAll(List.of(provided));
    }

    public static Region of(Location... provided) {
        return new Region(provided);
    }

    public Collection<Location> locations() {
        return locations;
    }

    public void add(Location location) {
        locations.add(location);
    }

    public long fencePrice() {
        return area() * perimeter();
    }

    public long fencePriceWithBulkDiscount() {
        return area() * numberOfSides();
    }

    long numberOfSides() {
        return locations
            .stream()
            .flatMap(loc -> {
                var angle1 = Set.of(loc, loc.left(), loc.up(), loc.left().up());
                var angle2 = Set.of(loc, loc.right(), loc.up(), loc.right().up());
                var angle3 = Set.of(loc, loc.left(), loc.down(), loc.left().down());
                var angle4 = Set.of(loc, loc.right(), loc.down(), loc.right().down());

                return Stream.of(angle1, angle2, angle3, angle4);
            })
            .distinct()
            .mapToLong(this::side)
            .sum();
    }

    private long side(Set<Location> angle) {
        List<Location> insides = angle.stream().filter(locations::contains).toList();
        long insideCount = insides.size();

        if (insideCount == 3 || insideCount == 1) {
            return 1;
        }
        if (insideCount == 2) {
            if (
                insides.getFirst().isOnSameRow(insides.getLast()) ||
                insides.getFirst().isOnSameColumn(insides.getLast())
            ) {
                return 0;
            }

            return 2;
        }

        return 0;
    }

    private long perimeter() {
        return locations
            .stream()
            .mapToLong(loc -> {
                var adjacents = loc.directAdjacentsInGrid(
                    0,
                    0,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE
                );

                return 4 - adjacents.stream().filter(locations::contains).count();
            })
            .sum();
    }

    long area() {
        return locations.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(locations, region.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(locations);
    }

    public Location anyLocation() {
        return locations.stream().findAny().orElse(null);
    }
}
