package advent_of_code.year2023.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
            int sumHorizontal = 100 * horizontalReflexion.stream().mapToInt(i -> (i + 1)).sum();

            int sumVertical = verticalReflexion.stream().mapToInt(i -> (i + 1)).sum();

            return sumHorizontal + sumVertical;
        }

        List<Integer> horizontalReflexion() {
            List<Integer> reflexions = new ArrayList<>();

            Optional<Integer> similarLine = findSimilarLine(0);

            while (similarLine.isPresent()) {
                boolean isReflexion = checkHorizontalReflexion(similarLine.get());
                if (isReflexion) {
                    reflexions.add(similarLine.get());
                }
                similarLine = findSimilarLine(similarLine.get() + 1);
            }

            return reflexions;
        }

        private Optional<Integer> findSimilarLine(int start) {
            for (int j = start; j < lines.size() - 1; j++) {
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

            while (similarColumn.isPresent()) {
                boolean isReflexion = checkVerticalReflexion(similarColumn.get());
                if (isReflexion) {
                    reflexions.add(similarColumn.get());
                }
                similarColumn = findSimilarColumn(similarColumn.get() + 1);
            }

            return reflexions;
        }

        private Optional<Integer> findSimilarColumn(int start) {
            for (int j = start; j < lines.get(0).length() - 1; j++) {
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

        int scorePart2() {
            var horizontalReflexion = horizontalReflexionPart2();
            var verticalReflexion = verticalReflexionPart2();

            System.out.println(String.join("\n", lines));
            System.out.println(horizontalReflexion);
            System.out.println(verticalReflexion);

            if (horizontalReflexion.isPresent() && verticalReflexion.isPresent()) {
                throw new RuntimeException();
            }

            int h = 100 * horizontalReflexion.map(i -> i + 1).orElse(0);
            int v = verticalReflexion.map(i -> i + 1).orElse(0);

            return h + v;
        }

        Optional<Integer> horizontalReflexionPart2() {
            Optional<Integer> similarLine = findSimilarLine(0);
            while (similarLine.isPresent()) {
                boolean isReflexion = checkHorizontalReflexionPart2(similarLine.get());
                if (isReflexion) {
                    return similarLine;
                }
                similarLine = findSimilarLine(similarLine.get() + 1);
            }

            Optional<Integer> smudgeSimilarLine = findSmudgedSimilarLine(0);
            while (smudgeSimilarLine.isPresent()) {
                boolean isReflexion = checkHorizontalReflexion(smudgeSimilarLine.get());
                if (isReflexion) {
                    return smudgeSimilarLine;
                }
                smudgeSimilarLine = findSmudgedSimilarLine(smudgeSimilarLine.get() + 1);
            }

            return Optional.empty();
        }

        private Optional<Integer> findSmudgedSimilarLine(int start) {
            for (int j = start; j < lines.size() - 1; j++) {
                if (potentialHorizontalSmudge(j, j + 1) == 1) {
                    return Optional.of(j);
                }
            }

            return Optional.empty();
        }

        private boolean checkHorizontalReflexionPart2(Integer rowIndex) {
            var count = Math.min(rowIndex, lines.size() - rowIndex - 2);

            return (
                IntStream.range(1, count + 1)
                    .map(i -> potentialHorizontalSmudge(rowIndex - i, rowIndex + i + 1))
                    .sum() ==
                1
            );
        }

        private int potentialHorizontalSmudge(int firstLine, int secondLine) {
            List<Integer> firsts = lines
                .get(firstLine)
                .chars()
                .mapToObj(c -> c == '#' ? 1 : 0)
                .toList();
            List<Integer> seconds = lines
                .get(secondLine)
                .chars()
                .mapToObj(c -> c == '#' ? 1 : 0)
                .toList();

            var sum = 0;
            for (int j = 0; j < firsts.size(); j++) {
                sum += ((firsts.get(j) + seconds.get(j)) % 2);
            }

            return sum;
        }

        Optional<Integer> verticalReflexionPart2() {
            Optional<Integer> similarColumn = findSimilarColumn(0);
            while (similarColumn.isPresent()) {
                boolean isReflexion = checkVerticalReflexionPart2(similarColumn.get());
                if (isReflexion) {
                    return similarColumn;
                }
                similarColumn = findSimilarColumn(similarColumn.get() + 1);
            }

            Optional<Integer> smudgeSimilarColumn = findSmudgedSimilarColumn(0);
            while (smudgeSimilarColumn.isPresent()) {
                boolean isReflexion = checkVerticalReflexion(smudgeSimilarColumn.get());
                if (isReflexion) {
                    return smudgeSimilarColumn;
                }
                smudgeSimilarColumn = findSmudgedSimilarColumn(smudgeSimilarColumn.get() + 1);
            }

            return Optional.empty();
        }

        private Optional<Integer> findSmudgedSimilarColumn(int start) {
            for (int j = start; j < lines.get(0).length() - 1; j++) {
                if (potentialVerticalSmudge(j, j + 1) == 1) {
                    return Optional.of(j);
                }
            }

            return Optional.empty();
        }

        private boolean checkVerticalReflexionPart2(Integer columnIndex) {
            var count = Math.min(columnIndex, lines.get(0).length() - columnIndex - 2);

            return (
                IntStream.range(1, count + 1)
                    .map(i -> potentialVerticalSmudge(columnIndex - i, columnIndex + i + 1))
                    .sum() ==
                1
            );
        }

        private int potentialVerticalSmudge(int firstColumn, int secondColumn) {
            List<Integer> firsts = lines
                .stream()
                .map(row -> row.charAt(firstColumn))
                .map(c -> c == '#' ? 1 : 0)
                .toList();
            List<Integer> seconds = lines
                .stream()
                .map(row -> row.charAt(secondColumn))
                .map(c -> c == '#' ? 1 : 0)
                .toList();

            var sum = 0;
            for (int j = 0; j < firsts.size(); j++) {
                sum += ((firsts.get(j) + seconds.get(j)) % 2);
            }

            return sum;
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

    public int part2(List<String> lines) {
        List<Pattern> patterns = groupByPattern(lines);

        return patterns.stream().mapToInt(Pattern::scorePart2).sum();
    }
}
