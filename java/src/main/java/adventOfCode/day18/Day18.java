
package adventOfCode.day18;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class Day18 {

    public Long part1(List<String> lines) {
        var instructions = lines
            .stream()
            .map(Day18::parse)
            .toList();

        List<Hole> holes = getHoles(instructions);
        List<List<String>> map = getMap(holes);
        printHoles(map);

        System.out.println();
        System.out.println();
        System.out.println();

        fillInner(map);

        printHoles(map);

        return countHoles(map);
    }

    private Long countHoles(List<List<String>> map) {
        return map.stream()
                  .mapToLong(row -> row.stream().mapToLong(c -> !".".equals(c) ? 1L : 0L).sum())
                  .sum();
    }


    private void fillInner(List<List<String>> map) {
        Location inner = new Location(1, map.get(0).indexOf("#") + 1);
        map.get(inner.i()).set(inner.j(), "O");

        Queue<Location> toVisit = new LinkedList<>();
        toVisit.add(inner);

        while (!toVisit.isEmpty()) {
            var location = toVisit.poll();

            List<Location> adjacent = location
                .adjacent(map)
                .stream()
                .filter(l -> map.get(l.i()).get(l.j()).equals("."))
                .toList();

            for (var position: adjacent) {
                map.get(position.i()).set(position.j(), "O");
            }

            toVisit.addAll(adjacent);
        }
    }

    private static boolean isHole(List<List<String>> map, int i, int j) {
        return map.get(i).get(j).equals("#");
    }

    @NotNull
    private static List<List<String>> getMap(List<Hole> holes) {
        int maxRow = holes.stream().mapToInt(Hole::i).max().orElseThrow();
        int minRow = holes.stream().mapToInt(Hole::i).min().orElseThrow();
        int maxColumn = holes.stream().mapToInt(Hole::j).max().orElseThrow();
        int minColumn = holes.stream().mapToInt(Hole::j).min().orElseThrow();

        List<List<String>> map = new ArrayList<>(maxRow - minRow);
        for (int i = 0; i <= maxRow - minRow; i++) {
            map.add(new ArrayList<>(maxColumn - minColumn));
            for (int j = 0; j <= maxColumn - minColumn; j++) {
                map.get(i).add(".");
            }
        }
        for (var hole: holes) {
            map.get(hole.i() - minRow).set(hole.j() - minColumn, "#");
        }
        return map;
    }

    private static List<Hole> getHoles(List<Instruction> instructions) {
        List<Hole> holes = new ArrayList<>();
        for (var instruction: instructions) {
            int lateralMove = 0;
            int verticalMove = 0;
            switch (instruction.direction()) {
                case 'U' -> verticalMove = -1;
                case 'D' -> verticalMove = 1;
                case 'R' -> lateralMove = 1;
                case 'L' -> lateralMove = -1;
                default -> throw new RuntimeException();
            }

            for (int i = 0; i < instruction.count(); i++) {
                if (holes.isEmpty()) {
                    Hole newHole = new Hole(0, 0);
                    holes.add(newHole);
                } else {
                    Hole current = holes.getLast();
                    Hole newHole = new Hole(current.i() + verticalMove, current.j() + lateralMove);
                    holes.add(newHole);
                }
            }
        }
        return holes;
    }

    private void printHoles(List<List<String>> map) {
        String printed = map.stream().map(r -> String.join("", r)).collect(Collectors.joining("\n"));
        System.out.println(printed);
    }

    private static Instruction parse(String row) {
        var split = row.split(" ");

        return new Instruction(split[0].charAt(0), Integer.parseInt(split[1]));
    }

    record Hole(int i, int j) {}
    record Instruction(
        char direction,
        long count
    ) {
        boolean isVertical() {
            return direction == 'U' || direction == 'D';
        }
    }

    public Long part2(List<String> lines) {
        var instructions = lines
            .stream()
            .map(Day18::parsePart2)
            .toList();

        return count(instructions);
    }

    static long count(List<Instruction> instructions) {
        long currentX = -1;
        long value = 0;
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            if (instruction.isVertical()) {
                var nextInstruction = instructions.get((i + 1) % instructions.size());
                var previousInstruction = instructions.get(i - 1);
                var sign1 = instruction.direction == 'U' ? 1 : -1;
                var sign2 = currentX < 0 ? 1 : -1;
                var sign3 = sign1 * sign2;

                long x = currentX < 0 ? Math.abs(currentX) : currentX + 1;
                long y = instruction.count();

                if (currentX > 0) {
                    if (previousInstruction.direction == 'R' && nextInstruction.direction == 'L') {
                        y += 1;
                    } else if (previousInstruction.direction == 'L' && nextInstruction.direction == 'R') {
                        y -= 1;
                    }
                } else {
                    if (sign3 < 0) {
                        x -= 1;
                    }
                    if (previousInstruction.direction == 'L' && nextInstruction.direction == 'R') {
                        y += 1;
                    } else if (previousInstruction.direction == 'R' && nextInstruction.direction == 'L') {
                        y -= 1;
                    }
                }

                long tmp = sign3 * x * y;
                System.out.println(currentX + " - " + instruction + " - " + tmp);
                value += tmp;
            } else {
                if (instruction.direction == 'L') {
                    currentX -= instruction.count();
                } else {
                    currentX += instruction.count();
                }
            }
        }
        return Math.abs(value);
    }

    static Instruction parsePart2(String row) {
        var split = row.split(" ");
        var instruction = split[2].replace("(", "").replace(")", "");

        var direction = switch (instruction.charAt(instruction.length() - 1)) {
            case '0' -> 'R';
            case '1' -> 'D';
            case '2' -> 'L';
            case '3' -> 'U';
            default -> throw new RuntimeException();
        };

        var hexadecimalString = instruction.substring(1, instruction.length() - 1);
        long hexadecimal = Long.parseLong(hexadecimalString, 16);

        return new Instruction(direction, hexadecimal);
    }
}

