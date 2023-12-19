
package adventOfCode.day18;

import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class Day18 {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

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

    static void printHoles(List<List<String>> map) {
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

        var map = mapPart2(instructions);

        long count = count(map, instructions);

        System.out.println("Result:");
        printHoles(map.map);
        return count;
    }

    record Map(List<Long> xs, List<Long> ys, List<List<String>> map) {}
     static Map mapPart2(List<Instruction> instructions) {
        List<Long> xs = new ArrayList<>();
        List<Long> ys = new ArrayList<>();

        long currentX = -1;
        long currentY = 0;
        xs.add(currentX);
        ys.add(currentY);
        for (var instruction: instructions) {
            switch (instruction.direction) {
                case 'U' -> currentY -= instruction.count;
                case 'D' -> currentY += instruction.count;
                case 'R' -> currentX += instruction.count;
                case 'L' -> currentX -= instruction.count;
            }

            if(!xs.contains(currentX)) {
                xs.add(currentX);
            }
            if(!ys.contains(currentY)) {
                ys.add(currentY);
            }
        }
        xs.sort(Comparator.comparing(l -> l));
        ys.sort(Comparator.comparing(l -> l));

        List<List<String>> map = new ArrayList<>(ys.size());
        for (int i = 0; i < ys.size(); i++) {
            map.add(new ArrayList<>(xs.size()));
            for (int j = 0; j < xs.size(); j++) {
                map.get(i).add(".");
            }
        }

        currentX = -1;
        currentY = 0;
        for (var instruction: instructions) {
            var newY = switch (instruction.direction) {
                case 'U' -> currentY - instruction.count;
                case 'D' -> currentY + instruction.count;
                default -> currentY;
            };

            var newX = switch (instruction.direction) {
                case 'R' -> currentX + instruction.count;
                case 'L' -> currentX - instruction.count;
                default -> currentX;
            };

            var indexX = xs.indexOf(currentX);
            var indexY = ys.indexOf(currentY);

            var indexNextX = xs.indexOf(newX);
            var indexNextY = ys.indexOf(newY);


            String sign = ".";

            switch (instruction.direction) {
                case 'U' -> {
                    sign = "^";
                }
                case 'D' -> {
                    sign = "v";
                }
                case 'L' -> {
                    sign = "<";
                }
                case 'R' -> {
                    sign = ">";
                }
            }

            for (int i = Math.min(indexY, indexNextY); i <= Math.max(indexY, indexNextY); i++) {
                for (int j = Math.min(indexX, indexNextX); j <= Math.max(indexX, indexNextX); j++) {
                    map.get(i).set(j, sign);
                }
            }
            map.get(indexY).set(indexX, "#");
            map.get(indexNextY).set(indexNextX, "#");

            currentX = newX;
            currentY = newY;
        }

        return new Map(xs, ys, map);
    }

    static long count(Map map, List<Instruction> instructions) {
        long currentX = -1;
        long currentY = 0;
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

                if (sign3 < 0) {
                    x -= 1;
                }

                if (currentX > 0) {
                    if (previousInstruction.direction == 'R' && nextInstruction.direction == 'L') {
                        y += 1;
                    } else if (previousInstruction.direction == 'L' && nextInstruction.direction == 'R') {
                        y -= 1;
                    }
                } else {
                    if (previousInstruction.direction == 'L' && nextInstruction.direction == 'R') {
                        y += 1;
                    } else if (previousInstruction.direction == 'R' && nextInstruction.direction == 'L') {
                        y -= 1;
                    }
                }

                long tmp = sign3 * x * y;
                System.out.println(currentX + " - " + instruction + " - " + tmp);
                value += tmp;

//                String color = ANSI_GREEN;
//                if (sign3 < 0) {
//                    color = ANSI_RED;
//                }
//
//                var nextY = currentY - sign1 * instruction.count;
//
//                long diffX = x - Math.abs(currentX);
//                int indexXStart = map.xs.indexOf(-1L);
//                int currentIndexX = map.xs.indexOf(currentX) + (int) diffX;
//
//                var xStart = Math.min(indexXStart + 1, currentIndexX);
//                var xEnd = Math.max(indexXStart + 1, currentIndexX);
//
//                long diffY = y - instruction.count;
//                int indexYCurrent = map.ys.indexOf(currentY);
//                int indexYNext = map.ys.indexOf(nextY);
//
//                var yStart = Math.min(indexYCurrent, indexYNext);
//                var yEnd = Math.max(indexYCurrent, indexYNext);
//
//                if (instruction.direction == 'U') {
//                    yStart -= (int) diffY;
//                } else {
//                    yEnd += (int) diffY;
//                }
//
//                int addY = 0;
//                if (yStart >= 0 && map.map.get(yStart).get(xStart).contains(color)) {
//                    addY += 1;
//                }
//
//                if (yEnd < map.ys.size() && map.map.get(yEnd).get(xStart).contains(color)) {
//                    addY -= 1;
//                }
//
//                for (int j = xStart; j < xEnd; j++) {
//                    for (int k = yStart + 1 + addY; k < yEnd + addY; k++) {
//                        map.map.get(k).set(j, color(map.map.get(k).get(j), color));
//                    }
//                    map.map.get(indexYCurrent + addY).set(j, color(map.map.get(indexYCurrent + addY).get(j), color));
//                }

//                printHoles(map.map);
//                System.out.println();
                currentY -= sign1 * instruction.count;
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

    private static String color(String s, String color) {
        String opposite = color.equals(ANSI_GREEN) ? ANSI_RED : ANSI_GREEN;
        if (s.contains(opposite)) {
            return stripColor(s);
        }
        if (s.contains(color)) {
            throw new RuntimeException();
        }
        return color + stripColor(s) + ANSI_RESET;
    }

    private static String stripColor(String s) {
        return s.replace(ANSI_RESET, "").replace(ANSI_GREEN, "").replace(ANSI_RED, "");
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

