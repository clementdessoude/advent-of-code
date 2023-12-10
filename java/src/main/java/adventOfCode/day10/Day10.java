
package adventOfCode.day10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

    public int part1(List<String> lines) {
        Area area = Area.from(lines);
        var loop = loop(area);

        return loop.size() / 2;
    }

    private List<Position> loop(Area area) {
        List<Position> loop = new LinkedList<>();

        Position current = area.getSource();
        loop.add(current);
        Position next = nextFromSource(current, area);
        loop.add(next);
        while (next.c() != 'S') {
            var tmp = next;
            next = next(current, next, area);
            loop.add(next);
            current = tmp;
        }

        return loop;
    }

    private Position nextFromSource(Position source, Area area) {
        return area
            .adjacent(source)
            .stream()
            .filter(adj -> isPossible(adj, source))
            .findFirst()
            .orElseThrow();
    }

    private Position next(Position first, Position second, Area area) {
        char symbol = second.c();
        return switch (symbol) {
            case '|' -> isBelow(first, second) ? goUp(second, area) : goDown(second, area);
            case '-' -> isRight(first, second) ? goLeft(second, area) : goRight(second, area);
            case '7' -> isBelow(first, second) ? goLeft(second, area) : goDown(second, area);
            case 'F' -> isBelow(first, second) ? goRight(second, area) : goDown(second, area);
            case 'L' -> isAbove(first, second) ? goRight(second, area) : goUp(second, area);
            case 'J' -> isAbove(first, second) ? goLeft(second, area) : goUp(second, area);
            default -> throw new RuntimeException();
        };
    }

    private boolean isAbove(Position first, Position second) {
        return first.i() < second.i();
    }

    private boolean isBelow(Position first, Position second) {
        return first.i() > second.i();
    }

    private boolean isRight(Position first, Position second) {
        return first.j() > second.j();
    }

    private static Position goUp(Position position, Area area) {
        return area.get(position.i() - 1, position.j());
    }

    private static Position goDown(Position position, Area area) {
        return area.get(position.i() + 1, position.j());
    }

    private static Position goLeft(Position position, Area area) {
        return area.get(position.i(), position.j() - 1);
    }

    private static Position goRight(Position position, Area area) {
        return area.get(position.i(), position.j() + 1);
    }

    private boolean isPossible(Position adj, Position source) {
        if (adj.j() < source.j()) {
            return Set.of('-', 'F', 'L').contains(adj.c());
        }
        if (adj.j() > source.j()) {
            return Set.of('-', '7', 'J').contains(adj.c());
        }
        if (adj.i() < source.i()) {
            return Set.of('|', '7', 'F').contains(adj.c());
        }
        if (adj.i() > source.i()) {
            return Set.of('|', 'L', 'J').contains(adj.c());
        }
        return false;
    }

    public Long part2(List<String> lines) {
        Area area = Area.from(lines);
        var loop = loop(area);

        Area curated = curated(area, loop);

        Queue<Position> enclosingNonPipes = curated.findEnclosingNonPipes();
        Set<Position> visited = new HashSet<>(enclosingNonPipes.stream().toList());

        while(!enclosingNonPipes.isEmpty()) {
            var source = enclosingNonPipes.poll();
            curated.set(source.i(), source.j(), 'O');

            List<Position> adjacent = curated.adjacent(source);

            List<Position> adjacentEmpty = adjacent
                .stream()
                .filter(p -> p.c() == '.')
                .filter(p -> !visited.contains(p))
                .toList();

            visited.addAll(adjacentEmpty);
            enclosingNonPipes.addAll(adjacentEmpty);
        }

        Queue<Position> toClean = new LinkedList<>();

        Position pipeElement = loop
            .stream()
            .filter(p -> p.c() == '-')
            .filter(p -> curated.adjacent(p).stream().anyMatch(adj -> adj.c() == 'O'))
            .map(p -> new Position(p.c(), p.i() + 1, p.j() + 1))
            .findFirst()
            .orElseThrow();

        Position up = goUp(pipeElement, curated);
        var lookup = up.c() == 'O' ? Direction.UP : Direction.DOWN;

        var current = pipeElement;
        var next = goRight(pipeElement, curated);
        var lookups = nextLookups(pipeElement, next, List.of(lookup));

        int count = 0;
        while (!next.equals(pipeElement)) {
            for (var lk: lookups) {
                var lookedPosition = switch (lk) {
                    case DOWN -> goDown(next, curated);
                    case UP -> goUp(next, curated);
                    case LEFT -> goLeft(next, curated);
                    case RIGHT -> goRight(next, curated);
                };
                if (lookedPosition.c() == '.') {
                    toClean.add(lookedPosition);
                }
            }

            var tmp = next;
            next = next(current, next, curated);
            current = tmp;
            lookups = nextLookups(current, next, lookups);
        }

        while(!toClean.isEmpty()) {
            var source = toClean.poll();
            curated.set(source.i(), source.j(), 'O');

            List<Position> adjacent = curated.adjacent(source);

            List<Position> adjacentEmpty = adjacent
                .stream()
                .filter(p -> p.c() == '.')
                .filter(p -> !visited.contains(p))
                .toList();

            visited.addAll(adjacentEmpty);
            enclosingNonPipes.addAll(adjacentEmpty);
        }

        curated.print();
        return curated.count('.');
    }

    enum Direction {
        UP(true),
        DOWN(true),
        RIGHT(false),
        LEFT(false);

        private final boolean isVertical;

        Direction(boolean isVertical) {
            this.isVertical = isVertical;
        }

        public boolean isVertical() {
            return isVertical;
        }

        public boolean isHorizontal() {
            return !isVertical;
        }
    }

    private List<Direction> nextLookups(
        Position adjacent,
        Position next,
        List<Direction> lookups
    ) {
        if (next.c() == '|') {
            List<Direction> list = lookups
                .stream()
                .filter(Direction::isHorizontal)
                .toList();

            if (!list.isEmpty()) {
                return list;
            }
            if (Set.of('F', 'L').contains(adjacent.c())) {
                return List.of(Direction.RIGHT);
            } else {
                return List.of(Direction.LEFT);
            }
        }

        if (next.c() == '-') {
            List<Direction> list = lookups
                .stream()
                .filter(Direction::isVertical)
                .toList();

            if (!list.isEmpty()) {
                return list;
            }
            if (Set.of('F', '7').contains(adjacent.c())) {
                return List.of(Direction.DOWN);
            } else {
                return List.of(Direction.UP);
            }
        }

        if (next.c() == 'F') {
            if (isBelow(adjacent, next)) {
                var first = lookups
                    .stream()
                    .filter(Direction::isHorizontal)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }
                var lookup = first.get();
                if (lookup == Direction.LEFT) {
                    return List.of(Direction.LEFT, Direction.UP);
                } else {
                    return List.of(Direction.DOWN);
                }
            } else {
                var first = lookups
                    .stream()
                    .filter(Direction::isVertical)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }
                var lookup = first.get();
                if (lookup == Direction.UP) {
                    return List.of(Direction.LEFT, Direction.UP);
                } else {
                    return List.of(Direction.RIGHT);
                }
            }
        }

        if (next.c() == '7') {
            if (isBelow(adjacent, next)) {
                var first = lookups
                    .stream()
                    .filter(Direction::isHorizontal)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }

                var lookup = first.get();
                if (lookup == Direction.LEFT) {
                    return List.of(Direction.DOWN);
                } else {
                    return List.of(Direction.RIGHT, Direction.UP);
                }
            } else {
                var first = lookups
                    .stream()
                    .filter(Direction::isVertical)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }

                var lookup = first.get();
                if (lookup == Direction.DOWN) {
                    return List.of(Direction.LEFT);
                } else {
                    return List.of(Direction.RIGHT, Direction.UP);
                }
            }
        }

        if (next.c() == 'J') {
            if (isAbove(adjacent, next)) {
                var first = lookups
                    .stream()
                    .filter(Direction::isHorizontal)
                    .findFirst();
                if (first.isEmpty()) {
                    return List.of();
                }

                var lookup = first.get();
                if (lookup == Direction.LEFT) {
                    return List.of(Direction.UP);
                } else {
                    return List.of(Direction.RIGHT, Direction.DOWN);
                }
            } else {
                Optional<Direction> first = lookups
                    .stream()
                    .filter(Direction::isVertical)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }

                var lookup = first.get();
                if (lookup == Direction.UP) {
                    return List.of(Direction.LEFT);
                } else {
                    return List.of(Direction.RIGHT, Direction.DOWN);
                }
            }
        }

        if (next.c() == 'L') {
            if (isAbove(adjacent, next)) {
                var first = lookups
                    .stream()
                    .filter(Direction::isHorizontal)
                    .findFirst();
                if (first.isEmpty()) {
                    return List.of();
                }
                var lookup = first.get();
                if (lookup == Direction.LEFT) {
                    return List.of(Direction.LEFT, Direction.DOWN);
                } else {
                    return List.of(Direction.UP);
                }
            } else {
                var first = lookups
                    .stream()
                    .filter(Direction::isVertical)
                    .findFirst();

                if (first.isEmpty()) {
                    return List.of();
                }
                var lookup = first.get();
                if (lookup == Direction.DOWN) {
                    return List.of(Direction.LEFT, Direction.DOWN);
                } else {
                    return List.of(Direction.RIGHT);
                }
            }
        }

        throw new RuntimeException("Position " + next);
    }

    private Area curated(Area area, List<Position> loop) {
        List<List<Character>> characters = new ArrayList<>();

        characters.add(IntStream.range(0, area.lines().get(0).size() + 2).mapToObj(i -> 'O').collect(Collectors.toList()));

        for (var chars: area.lines()) {
            List<Character> row = new ArrayList<>();
            row.add('O');
            for (char c: chars) {
                row.add('.');
            }
            row.add('O');
            characters.add(row);
        }

        characters.add(IntStream.range(0, area.lines().get(0).size() + 2).mapToObj(i -> 'O').collect(Collectors.toList()));

        for (var pos: loop) {
            characters.get(pos.i() + 1).set(pos.j() + 1, pos.c());
        }

        Set<Character> availableOptions = new HashSet<>(List.of(
                '-',
                '|',
                '7',
                'L',
                'F',
                'J'
        ));
        Position source = loop.get(0);
        Position next = loop.get(1);
        Position previous = loop.get(loop.size() - 2);

        System.out.println(source);
        System.out.println(next);
        System.out.println(previous);
        if (isAbove(source, next)) {
            availableOptions.removeAll(Set.of('-', 'L', 'J'));
        } else if (isRight(source, next)) {
            availableOptions.removeAll(Set.of('|', 'L', 'F'));
        } else if (isBelow(source, next)) {
            availableOptions.removeAll(Set.of('-', '7', 'F'));
        } else {
            availableOptions.removeAll(Set.of('|', '7', 'J'));
        }

        if (isAbove(source, previous)) {
            availableOptions.removeAll(Set.of('-', 'L', 'J'));
        } else if (isRight(source, previous)) {
            availableOptions.removeAll(Set.of('|', 'L', 'F'));
        } else if (isBelow(source, previous)) {
            availableOptions.removeAll(Set.of('-', '7', 'F'));
        } else {
            availableOptions.removeAll(Set.of('|', '7', 'J'));
        }

        if (availableOptions.size() != 1) {
            throw new RuntimeException();
        }

        characters.get(source.i() + 1).set(source.j() + 1, availableOptions.stream().findFirst().orElseThrow());

        return new Area(characters);
    }
}

// 107:123
