
package adventOfCode.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day13 {

    public int part1(List<String> lines) {
        List<Pattern> patterns = groupByPattern(lines);

        return patterns.stream().mapToInt(Pattern::score).sum();
    }

    class Pattern {
        private final List<String> lines;

        public Pattern(List<String> lines) {
            this.lines = lines;
        }

        int score() {
            var horizontalReflexion = horizontalReflexion();
            var verticalReflexion = verticalReflexion();

            System.out.println(String.join("\n", lines));
            System.out.println(horizontalReflexion);
            System.out.println(verticalReflexion);

            if (horizontalReflexion.size() + verticalReflexion.size() != 1) {
                throw new RuntimeException();
            }
            int sumHorizontal = 100 * horizontalReflexion
                    .stream()
                    .mapToInt(i -> (i + 1))
                    .sum();

            int sumVertical = verticalReflexion
                    .stream()
                    .mapToInt(i -> (i + 1))
                    .sum();

            return sumHorizontal + sumVertical;
        }

        List<Integer> horizontalReflexion() {
            List<Integer> reflexions = new ArrayList<>();

            Optional<Integer> similarLine = findSimilarLine(0);

            while(similarLine.isPresent()) {
                boolean isReflexion = checkHorizontalReflexion(similarLine.get());
                if (isReflexion) {
                    reflexions.add(similarLine.get());
                }
                similarLine = findSimilarLine(similarLine.get() + 1);
            }

            return reflexions;
        }

        private Optional<Integer> findSimilarLine(int start) {
            for (int j = start; j < lines.size() - 1 ; j++) {
                if (lines.get(j).equals(lines.get(j + 1))) {
                    return Optional.of(j);
                }
            }

            return Optional.empty();
        }

        private boolean checkHorizontalReflexion(Integer rowIndex) {
            var count = Math.min(rowIndex, lines.size() - rowIndex - 2);

            for (int i = 1; i <= count; i++) {
                if (!lines.get(rowIndex - i).equals(lines.get(rowIndex + 1 + i))) {
                    return false;
                }
            }
            return true;
        }

        List<Integer> verticalReflexion() {
            List<Integer> reflexions = new ArrayList<>();

            Optional<Integer> similarColumn = findSimilarColumn(0);

            while(similarColumn.isPresent()) {
                boolean isReflexion = checkVerticalReflexion(similarColumn.get());
                if (isReflexion) {
                    reflexions.add(similarColumn.get());
                }
                similarColumn = findSimilarColumn(similarColumn.get() + 1);
            }

            return reflexions;
        }

        private Optional<Integer> findSimilarColumn(int start) {
            for (int j = start; j < lines.get(0).length() - 1 ; j++) {
                boolean areEquals = true;
                for (String line : lines) {
                    if (line.charAt(j) != line.charAt(j + 1)) {
                        areEquals = false;
                        break;
                    }
                }

                if (areEquals) {
                    return Optional.of(j);
                }
            }

            return Optional.empty();
        }

        private boolean checkVerticalReflexion(Integer columnIndex) {
            var count = Math.min(columnIndex, lines.get(0).length() - columnIndex - 2);

            for (int i = 1; i <= count; i++) {
                boolean areEquals = true;
                for (String line : lines) {
                    if (line.charAt(columnIndex - i) != line.charAt(columnIndex + 1 + i)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private List<Pattern> groupByPattern(List<String> lines) {
        List<Pattern> patterns = new ArrayList<>();

        List<String> current = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) {
                patterns.add(new Pattern(current));
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }

        patterns.add(new Pattern(current));
        return patterns;
    }

    public Long part2(List<String> lines) {
        return null;
    }
}

