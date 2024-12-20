package advent_of_code.year2024.day20;

import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class Racetrack {

    private final List<String> racetrack;
    private final Location start;
    private final Location end;

    Racetrack(List<String> racetrack) {
        this.racetrack = racetrack;
        Location start = null;
        Location end = null;
        for (int y = 0; y < racetrack.size(); y++) {
            for (int x = 0; x < racetrack.getFirst().length(); x++) {
                if (racetrack.get(y).charAt(x) == 'S') {
                    start = new Location(x, y);
                } else if (racetrack.get(y).charAt(x) == 'E') {
                    end = new Location(x, y);
                }
            }
        }
        this.start = start;
        this.end = end;
    }

    long cheatCount(int picoSavedThreshold) {
        var validTrack = validTrack();
        var validTrackLocations = new HashSet<>(validTrack);
        List<Cheat> cheats = new ArrayList<>();
        for (int i = 0; i < validTrack.size(); i++) {
            var location = validTrack.get(i);
            var cheatOptions = cheatOptions(location);
            for (var option : cheatOptions) {
                if (validTrackLocations.contains(option)) {
                    var indexOf = validTrack.indexOf(option);
                    if (indexOf > i + 2) {
                        cheats.add(new Cheat(location, option, indexOf - i - 2));
                    }
                }
            }
        }

        return cheats
            .stream()
            .mapToInt(Cheat::savedPicos)
            .filter(saved -> saved >= picoSavedThreshold)
            .count();
    }

    long cheatCountWithSize(int picoSavedThreshold, int size) {
        var validTrack = validTrack();
        List<Cheat> cheats = new ArrayList<>();
        for (int i = 0; i < validTrack.size(); i++) {
            var location = validTrack.get(i);
            for (int j = i + picoSavedThreshold; j < validTrack.size(); j++) {
                Location option = validTrack.get(j);
                int distance = location.distanceTo(option);
                if (distance <= size) {
                    cheats.add(new Cheat(location, option, j - i - distance));
                }
            }
        }

        return cheats
            .stream()
            .mapToInt(Cheat::savedPicos)
            .filter(saved -> saved >= picoSavedThreshold)
            .count();
    }

    Collection<Location> cheatOptions(Location location) {
        return Stream.of(
            location.up().up(),
            location.left().left(),
            location.right().right(),
            location.down().down()
        )
            .filter(loc -> loc.isInGrid(0, 0, racetrack.getFirst().length(), racetrack.size()))
            .filter(this::isTrack)
            .toList();
    }

    private List<Location> validTrack() {
        List<Location> track = new ArrayList<>();
        track.add(start);
        while (!track.getLast().equals(end)) {
            try {
                track.add(next(track));
            } catch (NoSuchElementException e) {
                System.out.println("Error");
            }
        }
        return track;
    }

    private Location next(List<Location> track) {
        var location = track.getLast();
        return location
            .directAdjacentsInGrid()
            .stream()
            .filter(this::isTrack)
            .filter(loc -> !track.contains(loc))
            .findFirst()
            .orElseThrow();
    }

    private boolean isTrack(Location location) {
        return !isWall(location);
    }

    private boolean isWall(Location location) {
        return racetrack.get(location.y()).charAt(location.x()) == '#';
    }
}
