package advent_of_code.year2024.day8;

import advent_of_code.utils.CollectionUtils;
import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

record Antenna(String frequency, Location location) {
    static Set<Location> antinodes(Collection<Antenna> antennas) {
        return antinodesFromLocation(antennas.stream().map(Antenna::location).toList());
    }

    private static Set<Location> antinodesFromLocation(Collection<Location> antennasLocation) {
        var pairs = CollectionUtils.pairOf(antennasLocation);

        return pairs
            .stream()
            .map(Antenna::antinodes)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    private static Set<Location> antinodes(Pair<Location, Location> antennas) {
        var first = antennas.first();
        var second = antennas.second();

        var xDistance = first.x() - second.x();
        var yDistance = first.y() - second.y();

        var firstAntinode = new Location(first.x() + xDistance, first.y() + yDistance);
        var secondAntinode = new Location(second.x() - xDistance, second.y() - yDistance);

        return Set.of(firstAntinode, secondAntinode);
    }
}
