package advent_of_code.year2023.day17;

import java.util.*;
import java.util.stream.Collectors;

class SolverPart2 {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final List<List<Position>> positions;
    public final Map<Location, Map<Direction, Map<Integer, LocationWithDirection>>> heatLosses;

    public SolverPart2(List<List<Position>> positions) {
        this.positions = positions;
        heatLosses = positions
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toMap(p -> new Location(p.i(), p.j()), p -> new HashMap<>()));
    }

    void calculateShortestPathFromSource() {
        Location source = new Location(0, 0);
        LocationWithDirection tmp = new LocationWithDirection(source, null, 0, 0, List.of());
        Map<Direction, Map<Integer, LocationWithDirection>> heatLossesForSource = new HashMap<>();
        Map<Integer, LocationWithDirection> longs = new HashMap<>();
        longs.put(1, tmp);
        heatLossesForSource.put(Direction.RIGHT, longs);
        heatLosses.put(source, heatLossesForSource);
        List<Location> firstPath = new LinkedList<>();
        firstPath.add(source);

        heatLossesForSource = new HashMap<>();
        longs = new HashMap<>();
        longs.put(1, tmp);
        heatLossesForSource.put(Direction.DOWN, longs);
        heatLosses.put(source, heatLossesForSource);
        List<Location> secondPath = new LinkedList<>();
        secondPath.add(source);

        Set<LocationWithDirection> unsettledPositions = new HashSet<>();
        unsettledPositions.add(
            new LocationWithDirection(
                source.down(),
                Direction.DOWN,
                1,
                heatLossAt(source.down()),
                firstPath
            )
        );
        unsettledPositions.add(
            new LocationWithDirection(
                source.right(),
                Direction.RIGHT,
                1,
                heatLossAt(source.right()),
                secondPath
            )
        );

        while (!unsettledPositions.isEmpty()) {
            LocationWithDirection lowestHeatLoss = getLowestHeatLoss(unsettledPositions);
            unsettledPositions.remove(lowestHeatLoss);

            //            if (lowestHeatLoss.location().equals(new Location(0, 8))) {
            //                System.out.println(lowestHeatLoss);
            //            }
            for (LocationWithDirection adjacentPosition : getAdjacentPosition(lowestHeatLoss)) {
                takeAccount(unsettledPositions, adjacentPosition);
            }
        }
    }

    private Collection<LocationWithDirection> getAdjacentPosition(
        LocationWithDirection locationWithDirection
    ) {
        List<LocationWithDirection> result = new ArrayList<>();

        Location location = locationWithDirection.location();
        Direction currentDirection = locationWithDirection.direction();
        var currentPath = locationWithDirection.path();

        int maxLineSize = positions.size();
        int maxColumnSize = positions.get(0).size();
        if (location.j() > 0) {
            Location left = location.left();
            int count = currentDirection == Direction.LEFT ? locationWithDirection.count() + 1 : 1;
            if (
                canTake(
                    currentPath,
                    left,
                    currentDirection,
                    count,
                    Direction.LEFT,
                    locationWithDirection.count()
                )
            ) {
                long heatLoss = locationWithDirection.heatLoss() + heatLossAt(left);
                List<Location> newPath = new LinkedList<>(locationWithDirection.path());
                newPath.add(location);
                result.add(
                    new LocationWithDirection(left, Direction.LEFT, count, heatLoss, newPath)
                );
            }
        }

        if (location.j() < maxColumnSize - 1) {
            Location right = location.right();
            int count = currentDirection == Direction.RIGHT ? locationWithDirection.count() + 1 : 1;
            if (
                canTake(
                    currentPath,
                    right,
                    currentDirection,
                    count,
                    Direction.RIGHT,
                    locationWithDirection.count()
                )
            ) {
                long heatLoss = locationWithDirection.heatLoss() + heatLossAt(right);
                List<Location> newPath = new LinkedList<>(locationWithDirection.path());
                newPath.add(location);
                result.add(
                    new LocationWithDirection(right, Direction.RIGHT, count, heatLoss, newPath)
                );
            }
        }

        if (location.i() > 0) {
            Location up = location.up();
            int count = currentDirection == Direction.UP ? locationWithDirection.count() + 1 : 1;
            if (
                canTake(
                    currentPath,
                    up,
                    currentDirection,
                    count,
                    Direction.UP,
                    locationWithDirection.count()
                )
            ) {
                long heatLoss = locationWithDirection.heatLoss() + heatLossAt(up);
                List<Location> newPath = new LinkedList<>(locationWithDirection.path());
                newPath.add(location);
                result.add(new LocationWithDirection(up, Direction.UP, count, heatLoss, newPath));
            }
        }

        if (location.i() < maxLineSize - 1) {
            Location down = location.down();
            int count = currentDirection == Direction.DOWN ? locationWithDirection.count() + 1 : 1;
            if (
                canTake(
                    currentPath,
                    down,
                    currentDirection,
                    count,
                    Direction.DOWN,
                    locationWithDirection.count()
                )
            ) {
                long heatLoss = locationWithDirection.heatLoss() + heatLossAt(down);
                List<Location> newPath = new LinkedList<>(locationWithDirection.path());
                newPath.add(location);
                result.add(
                    new LocationWithDirection(down, Direction.DOWN, count, heatLoss, newPath)
                );
            }
        }

        return result;
    }

    private static boolean canTake(
        List<Location> currentPath,
        Location node,
        Direction currentDirection,
        int newDirectionCount,
        Direction nextDirection,
        int previousDirectionCount
    ) {
        if (currentPath.contains(node)) {
            return false;
        }
        if (currentDirection.opposite() == nextDirection) {
            return false;
        }
        if (newDirectionCount > 10) {
            return false;
        }

        if (currentDirection != nextDirection && previousDirectionCount < 4) {
            return false;
        }

        return true;
    }

    private void takeAccount(
        Set<LocationWithDirection> unsettledPositions,
        LocationWithDirection locationWithDirection
    ) {
        Map<Integer, LocationWithDirection> newHeatLoss = heatLosses
            .get(locationWithDirection.location())
            .getOrDefault(locationWithDirection.direction(), new HashMap<>());

        for (int i = locationWithDirection.count(); i <= 10; i++) {
            if (
                newHeatLoss.containsKey(i) &&
                newHeatLoss.get(i).heatLoss() > locationWithDirection.heatLoss()
            ) {
                newHeatLoss.remove(i);
            }
        }

        if (!newHeatLoss.containsKey(locationWithDirection.count())) {
            newHeatLoss.put(locationWithDirection.count(), locationWithDirection);
            unsettledPositions.add(locationWithDirection);
        }

        heatLosses
            .get(locationWithDirection.location())
            .put(locationWithDirection.direction(), newHeatLoss);
    }

    ////    private boolean takeAccount(Position adjacentPosition, Position current) {
    ////        Direction direction = adjacentPosition.direction(current);
    ////        Location nextLocation = new Location(
    ////            adjacentPosition.heatLoss(),
    ////            adjacentPosition.j()
    ////        );
    ////        Location currentLocation = new Location(current.heatLoss(), current.j());
    ////
    ////        Map<Direction, Map<Integer, Long>> currentLowest = heatLosses.get(
    ////            currentLocation);
    ////        Map<Integer, Long> newLowest = new HashMap<>();
    ////
    ////        var min = currentLowest
    ////            .entrySet()
    ////            .stream()
    ////            .filter(entry -> entry.getKey() != direction)
    ////            .flatMapToLong(entry -> entry
    ////                .getValue()
    ////                .values()
    ////                .stream()
    ////                .mapToLong(l -> l))
    ////            .min();
    ////
    ////        if (min.isPresent()) {
    ////            newLowest.put(
    ////                1,
    ////                min.getAsLong()
    ////                    + adjacentPosition.heatLossToGetThere()
    ////            );
    ////        }
    ////
    ////        var sameDirection = currentLowest
    ////            .entrySet()
    ////            .stream()
    ////            .filter(entry -> entry.getKey() == direction)
    ////            .findAny();
    ////
    ////        if (sameDirection.isPresent()) {
    ////            Map<Integer, Long> value = sameDirection.get().getValue();
    ////            value
    ////                .entrySet()
    ////                .stream()
    ////                .filter(entry -> entry.getKey() != 3)
    ////                .filter(entry -> min.isEmpty()
    ////                    || entry.getValue() < min.getAsLong())
    ////                .map(entry -> Map.entry(
    ////                    entry.getKey() + 1,
    ////                    entry.getValue()
    ////                        + adjacentPosition.heatLossToGetThere()
    ////                ))
    ////                .forEach(e -> newLowest.put(e.getKey(), e.getValue()));
    ////        }
    ////
    ////        boolean hasAddAdjacent = false;
    ////        Map<Integer, Long> longs = heatLosses.get(nextLocation).get(direction);
    ////        if (longs == null || longs.isEmpty()) {
    ////            heatLosses.get(nextLocation).put(direction, newLowest);
    ////            hasAddAdjacent = true;
    ////        } else {
    ////            Map<Integer, Long> updatedLongs = new HashMap<>();
    ////
    ////            Long currentMin = longs.getOrDefault(1, Long.MAX_VALUE);
    ////
    ////            for (int heatLoss = 1; heatLoss <= 3; heatLoss++) {
    ////                long min1 = Math.min(
    ////                    longs.getOrDefault(heatLoss, Long.MAX_VALUE),
    ////                    newLowest.getOrDefault(heatLoss, Long.MAX_VALUE)
    ////                );
    ////                if (currentMin <= min1) {
    ////                    break;
    ////                }
    ////                updatedLongs.put(heatLoss, min1);
    ////                if (min1 != longs.getOrDefault(heatLoss, -1L)) {
    ////                    hasAddAdjacent = true;
    ////                }
    ////                currentMin = min1;
    ////            }
    ////        }
    ////
    ////        return hasAddAdjacent;
    ////    }
    //
    //    public void displayPath(Position destination) {
    //        List<List<String>> map = new ArrayList<>();
    //        for (var row: positions) {
    //            var rowList = new ArrayList<String>();
    //            for (Position position : row) {
    //                rowList.add(ANSI_RED + String.valueOf(position.heatLossToGetThere() + ANSI_RESET));
    //            }
    //
    //            map.add(rowList);
    //        }
    //
    //        Location destinationLocation = new Location(destination.heatLoss(), destination.j());
    //
    //        Location source = new Location(0, 0);
    //        Direction currentDirection = null;
    //        int times = 0;
    //        while (!destinationLocation.equals(source)) {
    //            var direction = getFrom(destinationLocation, currentDirection, times);
    //            var newDestinationLocation = switch (direction) {
    //                case LEFT -> new Location(destinationLocation.heatLoss(), destinationLocation.j() + 1);
    //                case UP -> new Location(destinationLocation.heatLoss() + 1, destinationLocation.j());
    //                case RIGHT -> new Location(destinationLocation.heatLoss(), destinationLocation.j() - 1);
    //                case DOWN -> new Location(destinationLocation.heatLoss() - 1, destinationLocation.j());
    //            };
    //            String c = switch (direction) {
    //                case LEFT -> ANSI_GREEN + "<" + ANSI_RESET;
    //                case UP -> ANSI_GREEN + "^" + ANSI_RESET;
    //                case RIGHT -> ANSI_GREEN + ">" + ANSI_RESET;
    //                case DOWN -> ANSI_GREEN + "v" + ANSI_RESET;
    //            };
    //            times = currentDirection == direction ? times + 1 : 1;
    //            currentDirection = direction;
    //
    //            map.get(destinationLocation.heatLoss()).set(destinationLocation.j(), c);
    //            destinationLocation = newDestinationLocation;
    //        }
    //
    //        System.out.println(
    //            String.join("\n", map.stream().map(r -> String.join("", r)).toList())
    //        );
    //
    //    }

    //    private Direction getFrom(
    //        Location destination,
    //        Direction direction,
    //        int times
    //    ) {
    //        var entry = heatLosses
    //            .get(destination)
    //            .entrySet()
    //            .stream()
    //            .map(e -> new MinValue(e.getKey(), e
    //                .getValue()
    //                .entrySet()
    //                .stream()
    //                .filter(longEntry -> e.getKey() != direction || (times + longEntry.getKey() < 4))
    //                .mapToLong(Map.Entry::getValue)
    //                .min()
    //                .orElse(Long.MAX_VALUE)))
    //            .min(Comparator.comparing(MinValue::minValue))
    //            .orElseThrow();
    //
    //        return entry.direction;
    //    }

    //    record Tmp(Direction direction, Position position) {
    //    }
    //
    //    record MinValue(Direction direction, long minValue) {}

    private LocationWithDirection getLowestHeatLoss(Set<LocationWithDirection> unsettledPositions) {
        return unsettledPositions
            .stream()
            .min(Comparator.comparing(LocationWithDirection::heatLoss))
            .orElseThrow();
    }

    int heatLossAt(Location location) {
        return positions.get(location.i()).get(location.j()).heatLossToGetThere();
    }
}
