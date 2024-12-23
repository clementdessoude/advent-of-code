package advent_of_code.year2023.day16;

import java.util.*;

class BeamDirections {

    private final Map<Position, Set<BeamDirection>> beamDirections = new HashMap<>();

    boolean add(Position position, BeamDirection beamDirection) {
        if (!beamDirections.containsKey(position)) {
            Set<BeamDirection> newBeamDirections = new HashSet<>();
            beamDirections.put(position, newBeamDirections);
        }

        if (beamDirections.get(position).contains(beamDirection)) {
            return false;
        }
        beamDirections.get(position).add(beamDirection);
        return true;
    }

    public Set<BeamDirection> get(Position position) {
        return beamDirections.getOrDefault(position, new HashSet<>());
    }
}
