package advent_of_code.year2024.day8;

import advent_of_code.utils.Display;
import advent_of_code.utils.Location;
import java.util.*;

final class City {

    private final Map<String, Set<Antenna>> antennaByFrequency = new HashMap<>();
    private final int cityLimitX;
    private final int cityLimitY;

    City(List<String> rows) {
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(y).length(); x++) {
                if (rows.get(y).charAt(x) != '.') {
                    var frequency = String.valueOf(rows.get(y).charAt(x));
                    antennaByFrequency
                        .computeIfAbsent(frequency, k -> new HashSet<>())
                        .add(new Antenna(frequency, new Location(x, y)));
                }
            }
        }

        cityLimitX = rows.getFirst().length();
        cityLimitY = rows.size();
    }

    int countAntinodesInCity() {
        List<Location> antinodes = antennaByFrequency
            .values()
            .stream()
            .map(Antenna::antinodes)
            .flatMap(Collection::stream)
            .filter(location -> location.isInGrid(0, 0, cityLimitX, cityLimitY))
            .distinct()
            .toList();

        //        display(antinodes);
        return antinodes.size();
    }

    private void display(List<Location> antinodes) {
        List<List<String>> grid = new ArrayList<>();
        for (int i = 0; i < cityLimitY; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < cityLimitX; j++) {
                grid.get(i).add(".");
            }
        }

        for (Location location : antinodes) {
            grid.get(location.y()).set(location.x(), "#");
        }

        antennaByFrequency
            .values()
            .stream()
            .flatMap(Collection::stream)
            .forEach(antenna ->
                grid.get(antenna.location().y()).set(antenna.location().x(), antenna.frequency())
            );

        Display.display(grid);
    }
}
