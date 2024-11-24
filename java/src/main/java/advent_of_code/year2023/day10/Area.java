package advent_of_code.year2023.day10;

import static java.util.stream.Collectors.joining;

import java.util.*;
import java.util.stream.Collectors;

record Area(List<List<Character>> lines) {
    static Area from(List<String> characters) {
        return new Area(characters
                            .stream()
                            .map(row -> row
                                .chars()
                                .mapToObj(c -> (char) c)
                                .collect(Collectors.toList()))
                            .collect(Collectors.toList()));
    }

    Position getSource() {
        for (int i = 0; i < lines.size(); i++) {
            var row = lines.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == 'S') {
                    return new Position('S', i, j);
                }
            }
        }
        throw new RuntimeException();
    }
    List<Position> adjacent(Position position) {
        return adjacent(position.i(), position.j());
    }

    List<Position> adjacent(int i, int j) {
        List<Position> result = new ArrayList<>();
        if (i > 0) {
            result.add(new Position(lines.get(i - 1).get(j), i - 1, j));
        }
        if (i < lines.size() - 1) {
            result.add(new Position(lines.get(i + 1).get(j), i + 1, j));
        }

        if (j > 0) {
            result.add(new Position(lines.get(i).get(j - 1), i, j - 1));
        }
        if (j < lines.get(0).size() - 1) {
            result.add(new Position(lines.get(i).get(j + 1), i, j + 1));
        }

        return result;
    }

    public Position get(int i, int j) {
        return new Position(lines.get(i).get(j), i, j);
    }

    public Queue<Position> findEnclosingNonPipes() {
        Queue<Position> result = new LinkedList<>();
        for (int j = 1; j < lines.get(0).size() - 1; j++) {
            if (lines.get(1).get(j) == '.') {
                result.add(new Position('.', 0, j));
            }
            if (lines.get(lines.size() - 2).get(j) == '.') {
                result.add(new Position('.', lines.size() - 1, j));
            }
        }

        var columnCount = lines.get(0).size() - 1;
        for (int i = 1; i < lines.size() - 1; i++) {
            if (lines.get(i).get(1) == '.') {
                result.add(new Position('.', i, 0));
            }
            if (lines.get(i).get(columnCount - 1) == '.') {
                result.add(new Position('.', i, columnCount - 1));
            }
        }

        return result;
    }

    void set(int i, int j, char c) {
        lines.get(i).set(j, c);
    }

    void print() {
        lines.forEach(l -> System.out.println(
            l
                .stream()
                .map(String::valueOf)
                .collect(joining())
        ));
    }

    long count(char c) {
        return lines.stream()
            .flatMap(Collection::stream)
            .filter(ch -> ch == c)
            .count();
    }
}
