package advent_of_code.year2025.day1;

import java.util.List;

class Day1 {

    Long part1(List<String> lines) {
        long result = 0;
        int currentPosition = 50;
        for (var line : lines) {
            int value = Integer.parseInt(line.substring(1)) * (line.startsWith("R") ? 1 : -1);
            currentPosition = ((currentPosition + value) + 100) % 100;
            if (currentPosition == 0) {
                result += 1;
            }
        }

        return result;
    }

    Long part2(List<String> lines) {
        long result = 0;
        int currentPosition = 50;
        for (var line : lines) {
            int value = Integer.parseInt(line.substring(1)) * (line.startsWith("R") ? 1 : -1);
            int toAdd = Math.abs(value / 100);
            int modulus = value % 100;

            var sum = modulus + currentPosition;
            if (sum >= 100 || (sum <= 0 && currentPosition > 0)) {
                result += 1;
            }
            result += toAdd;
            currentPosition = (sum + 100) % 100;
        }

        return result;
    }
}
