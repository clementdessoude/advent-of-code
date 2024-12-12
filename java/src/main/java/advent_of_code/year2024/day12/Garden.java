package advent_of_code.year2024.day12;

import static advent_of_code.utils.CollectionUtils.notIn;

import advent_of_code.utils.Location;
import java.util.*;

final class Garden {

    private final List<String> garden;

    Garden(List<String> garden) {
        this.garden = garden;
    }

    Collection<Region> regions() {
        List<Region> regions = new ArrayList<>();
        Set<Location> visited = new HashSet<>();
        for (int y = 0; y < garden.size(); y++) {
            for (int x = 0; x < garden.get(y).length(); x++) {
                Location loc = new Location(x, y);
                if (visited.contains(loc)) {
                    continue;
                }

                Region region = getRegion(loc);
                visited.addAll(region.locations());
                regions.add(region);
            }
        }

        return regions;
    }

    private Region getRegion(Location loc) {
        var value = charAt(loc);
        Region region = new Region();

        Set<Location> availables = new HashSet<>();
        availables.add(loc);
        while (!availables.isEmpty()) {
            var current = availables.iterator().next();
            availables.remove(current);

            if (charAt(current) != value) {
                continue;
            }
            region.add(current);
            availables.addAll(
                notIn(
                    current.directAdjacentsInGrid(0, 0, garden.getFirst().length(), garden.size()),
                    region.locations()
                )
            );
        }

        return region;
    }

    char charAt(Location loc) {
        return garden.get(loc.y()).charAt(loc.x());
    }
}
