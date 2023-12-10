
package adventOfCode.day10;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
            .adjacents(source)
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


        return null;
    }
}
