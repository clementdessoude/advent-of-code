package adventOfCode.day17;

import java.util.*;
import java.util.stream.Collectors;

class Djikstra {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final List<List<Position>> positions;
    public final Map<Location, Map<Direction, Map<Integer, Long>>> lowest;

    public Djikstra(List<List<Position>> positions) {
        this.positions = positions;
        lowest = positions
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toMap(
                p -> new Location(p.i(), p.j()),
                p -> new HashMap<>()
            ));
    }

    void calculateShortestPathFromSource() {
//        Map<Direction, Map<Integer, Long>> v = new HashMap<>();
//        Map<Integer, Long> longs = new HashMap<>();
//        longs.put(1, (long) positions.get(0).get(1).heatLossToGetThere());
//        v.put(Direction.RIGHT, longs);
//        lowest.put(new Location(0, 1), v);
//
//        v = new HashMap<>();
//        longs = new HashMap<>();
//        longs.put(1, (long) positions.get(1).get(0).heatLossToGetThere());
//        v.put(Direction.DOWN, longs);
//        lowest.put(new Location(1, 0), v);
//
//        Set<Position> unsettledPositions = new HashSet<>();
//
//        unsettledPositions.add(positions.get(0).get(1));
//        unsettledPositions.add(positions.get(1).get(0));


        Map<Direction, Map<Integer, Long>> v = new HashMap<>();
        Map<Integer, Long> longs = new HashMap<>();
        longs.put(1, 0L);
        v.put(Direction.RIGHT, longs);
        lowest.put(new Location(0, 0), v);

        v = new HashMap<>();
        longs = new HashMap<>();
        longs.put(1, 0L);
        v.put(Direction.DOWN, longs);
        lowest.put(new Location(0, 0), v);

        Set<Position> unsettledPositions = new HashSet<>();

        unsettledPositions.add(positions.get(0).get(0));

        while (!unsettledPositions.isEmpty()) {
            Tmp tmp = getLowestHeatLoss(unsettledPositions);
            Position currentPosition = tmp.position();
            unsettledPositions.remove(currentPosition);

            for (Position adjacentPosition : getAdjacentPosition(currentPosition, positions, tmp.direction())) {
                var hasAddAdjacent = takeAccount(
                    adjacentPosition,
                    currentPosition
                );
                if (hasAddAdjacent) {
                    unsettledPositions.add(adjacentPosition);
                }
            }
        }
    }

    private boolean takeAccount(Position adjacentPosition, Position current) {
        Direction direction = adjacentPosition.direction(current);
        Location nextLocation = new Location(
            adjacentPosition.i(),
            adjacentPosition.j()
        );
        Location currentLocation = new Location(current.i(), current.j());

        Map<Direction, Map<Integer, Long>> currentLowest = lowest.get(
            currentLocation);
        Map<Integer, Long> newLowest = new HashMap<>();

        var min = currentLowest
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey() != direction)
            .flatMapToLong(entry -> entry
                .getValue()
                .values()
                .stream()
                .mapToLong(l -> l))
            .min();

        if (min.isPresent()) {
            newLowest.put(
                1,
                min.getAsLong()
                    + adjacentPosition.heatLossToGetThere()
            );
        }

        var sameDirection = currentLowest
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey() == direction)
            .findAny();

        if (sameDirection.isPresent()) {
            Map<Integer, Long> value = sameDirection.get().getValue();
            value
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != 3)
                .filter(entry -> min.isEmpty()
                    || entry.getValue() < min.getAsLong())
                .map(entry -> Map.entry(
                    entry.getKey() + 1,
                    entry.getValue()
                        + adjacentPosition.heatLossToGetThere()
                ))
                .forEach(e -> newLowest.put(e.getKey(), e.getValue()));
        }

        boolean hasAddAdjacent = false;
        Map<Integer, Long> longs = lowest.get(nextLocation).get(direction);
        if (longs == null || longs.isEmpty()) {
            lowest.get(nextLocation).put(direction, newLowest);
            hasAddAdjacent = true;
        } else {
            Map<Integer, Long> updatedLongs = new HashMap<>();

            Long currentMin = longs.getOrDefault(1, Long.MAX_VALUE);

            for (int i = 1; i <= 3; i++) {
                long min1 = Math.min(
                    longs.getOrDefault(i, Long.MAX_VALUE),
                    newLowest.getOrDefault(i, Long.MAX_VALUE)
                );
                if (currentMin <= min1) {
                    break;
                }
                updatedLongs.put(i, min1);
                if (min1 != longs.getOrDefault(i, -1L)) {
                    hasAddAdjacent = true;
                }
                currentMin = min1;
            }
        }

        return hasAddAdjacent;
    }

    private List<Position> getAdjacentPosition(
        Position currentPosition,
        List<List<Position>> positions,
        Direction currentDirection
    ) {
        List<Position> adjacents = new ArrayList<>();

        int i = currentPosition.i();
        int j = currentPosition.j();

        if (i > 0 && currentDirection.opposite() != Direction.UP) {
            adjacents.add(positions.get(i - 1).get(j));
        }

        if (i < positions.size() - 1 && currentDirection.opposite() != Direction.DOWN) {
            adjacents.add(positions.get(i + 1).get(j));
        }

        if (j > 0 && currentDirection.opposite() != Direction.LEFT) {
            adjacents.add(positions.get(i).get(j - 1));
        }

        if (j < positions.get(0).size() - 1
            && currentDirection.opposite() != Direction.RIGHT) {
            adjacents.add(positions.get(i).get(j + 1));
        }

        return adjacents;
    }

    public void displayPath(Position destination) {
        List<List<String>> map = new ArrayList<>();
        for (var row: positions) {
            var rowList = new ArrayList<String>();
            for (Position position : row) {
                rowList.add(ANSI_RED + String.valueOf(position.heatLossToGetThere() + ANSI_RESET));
            }

            map.add(rowList);
        }

        Location destinationLocation = new Location(destination.i(), destination.j());

        Location source = new Location(0, 0);
        Direction currentDirection = null;
        int times = 0;
        while (!destinationLocation.equals(source)) {
            var direction = getFrom(destinationLocation, currentDirection, times);
            var newDestinationLocation = switch (direction) {
                case LEFT -> new Location(destinationLocation.i(), destinationLocation.j() + 1);
                case UP -> new Location(destinationLocation.i() + 1, destinationLocation.j());
                case RIGHT -> new Location(destinationLocation.i(), destinationLocation.j() - 1);
                case DOWN -> new Location(destinationLocation.i() - 1, destinationLocation.j());
            };
            String c = switch (direction) {
                case LEFT -> ANSI_GREEN + "<" + ANSI_RESET;
                case UP -> ANSI_GREEN + "^" + ANSI_RESET;
                case RIGHT -> ANSI_GREEN + ">" + ANSI_RESET;
                case DOWN -> ANSI_GREEN + "v" + ANSI_RESET;
            };
            times = currentDirection == direction ? times + 1 : 1;
            currentDirection = direction;

            map.get(destinationLocation.i()).set(destinationLocation.j(), c);
            destinationLocation = newDestinationLocation;
        }

        System.out.println(
            String.join("\n", map.stream().map(r -> String.join("", r)).toList())
        );

    }

    private Direction getFrom(
        Location destination,
        Direction direction,
        int times
    ) {
        var entry = lowest
            .get(destination)
            .entrySet()
            .stream()
            .map(e -> new MinValue(e.getKey(), e
                .getValue()
                .entrySet()
                .stream()
                .filter(longEntry -> e.getKey() != direction || (times + longEntry.getKey() < 4))
                .mapToLong(Map.Entry::getValue)
                .min()
                .orElse(Long.MAX_VALUE)))
            .min(Comparator.comparing(MinValue::minValue))
            .orElseThrow();

        return entry.direction;
    }

    record Tmp(Direction direction, Position position) {
    }

    record MinValue(Direction direction, long minValue) {}

    private Tmp getLowestHeatLoss(Set<Position> unsettledPositions) {
        Position lowestHeatLossPosition = null;
        Direction direction = null;
        long lowestHeatLoss = Long.MAX_VALUE;
        for (Position position : unsettledPositions) {
            var location = new Location(position.i(), position.j());

            var entry = lowest
                .get(location)
                .entrySet()
                .stream()
                .map(e -> new MinValue(e.getKey(), e
                                                                 .getValue()
                                                                 .values()
                                                                 .stream()
                                                                 .mapToLong(l -> l)
                                                                 .min()
                                                                 .orElse(Long.MAX_VALUE)))
                .min(Comparator.comparing(MinValue::minValue));

            if (entry.isEmpty()) {
                throw new RuntimeException();
            }

            long positionHeatLoss = entry.get().minValue();
            if (positionHeatLoss < lowestHeatLoss) {
                lowestHeatLoss = positionHeatLoss;
                lowestHeatLossPosition = position;
                direction = entry.get().direction();
            }
        }
        return new Tmp(direction, lowestHeatLossPosition);
    }

}
