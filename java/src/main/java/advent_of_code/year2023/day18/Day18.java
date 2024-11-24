
package advent_of_code.year2023.day18;

import java.util.*;
import java.util.stream.Collectors;

public class Day18 {

    public Long part1(List<String> lines) {
        var instructions = lines
            .stream()
            .map(Day18::parse)
            .toList();

        return holesCount(instructions);
    }

    private static Instruction parse(String row) {
        var split = row.split(" ");

        return new Instruction(split[0].charAt(0), Integer.parseInt(split[1]));
    }

    private record Map(List<Long> xs, List<Long> ys, List<List<String>> map) {}
    private static Map getMap(List<Instruction> instructions) {
        List<Long> xs = new ArrayList<>();
        List<Long> ys = new ArrayList<>();

        long currentX = -1;
        long currentY = 0;
        xs.add(currentX);
        ys.add(currentY);
        for (var instruction: instructions) {
            switch (instruction.direction()) {
                case 'U' -> currentY -= instruction.count();
                case 'D' -> currentY += instruction.count();
                case 'R' -> currentX += instruction.count();
                case 'L' -> currentX -= instruction.count();
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

        List<List<String>> map = new ArrayList<>(2 * ys.size());
        for (int i = 0; i <  2 * ys.size(); i++) {
            map.add(new ArrayList<>(xs.size()));
            for (int j = 0; j < 2 * xs.size(); j++) {
                map.get(i).add(".");
            }
        }

        currentX = -1;
        currentY = 0;
        for (var instruction: instructions) {
            var newY = switch (instruction.direction()) {
                case 'U' -> currentY - instruction.count();
                case 'D' -> currentY + instruction.count();
                default -> currentY;
            };

            var newX = switch (instruction.direction()) {
                case 'R' -> currentX + instruction.count();
                case 'L' -> currentX - instruction.count();
                default -> currentX;
            };

            var indexX = xs.indexOf(currentX);
            var indexY = ys.indexOf(currentY);

            var indexNextX = xs.indexOf(newX);
            var indexNextY = ys.indexOf(newY);

            String sign = switch (instruction.direction()) {
                case 'U' -> "^";
                case 'D' -> "v";
                case 'L' -> "<";
                case 'R' -> ">";
                default -> throw new RuntimeException();
            };

            for (int i = 2 * Math.min(indexY, indexNextY); i <= 2 * Math.max(indexY, indexNextY); i++) {
                for (int j = 2 * Math.min(indexX, indexNextX); j <= 2 * Math.max(indexX, indexNextX); j++) {
                    map.get(i).set(j, sign);
                }
            }
            map.get(2 * indexY).set(2 * indexX, "#");
            map.get(2 * indexNextY).set(2 * indexNextX, "#");

            currentX = newX;
            currentY = newY;
        }

        return new Map(xs, ys, map);
    }

    static long holesCount(List<Instruction> instructions) {
        Map map = getMap(instructions);
        fillInner(map.map);

        // print(map.map);
        return count(map, instructions);
    }

    private static void fillInner(List<List<String>> map) {
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

    private static long count(Map map, List<Instruction> instructions) {
        long result = 0;
        List<List<String>> lists = map.map;
        for (int i = 0; i < lists.size() - 1; i++) {
            var row = lists.get(i);
            long factor = i % 2 == 0 ? 1 : (map.ys.get((i + 1) / 2) - map.ys.get((i - 1) / 2) - 1);
            long rowResult = 0;
            for (int j = 0; j < row.size(); j++) {
                if (!row.get(j).equals(".")) {
                    int k = j + 1;
                    while (!row.get(k).equals(".")) {
                        k++;
                    }
                    rowResult += factor * (map.xs.get((k - 1) / 2) - map.xs.get(j / 2) + 1);
                    j = k;
                }
            }
            result += rowResult;
        }
        return result;
    }

    static void print(List<List<String>> map) {
        String printed = map.stream().map(r -> String.join("", r)).collect(Collectors.joining("\n"));
        System.out.println(printed);
    }

    public Long part2(List<String> lines) {
        var instructions = lines
            .stream()
            .map(Day18::parsePart2)
            .toList();

        return holesCount(instructions);
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

