package adventOfCode.day10;

import java.util.ArrayList;
import java.util.List;
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

    List<Position> adjacents(Position position) {
        return adjacents(position.i(), position.j());
    }

    List<Position> adjacents(int i, int j) {
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
}
