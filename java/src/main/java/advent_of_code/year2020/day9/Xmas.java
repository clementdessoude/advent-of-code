package advent_of_code.year2020.day9;

import java.util.*;

class Xmas {

    private final List<Long> data;
    private final int preamble;

    Xmas(List<Long> data, int preamble) {
        this.data = Collections.unmodifiableList(data);
        this.preamble = preamble;
    }

    long firstInvalidNumber() {
        Map<Long, Integer> options = new HashMap<>();
        for (int i = 0; i < preamble; i++) {
            for (int j = i + 1; j < preamble; j++) {
                var option = data.get(i) + data.get(j);
                options.computeIfAbsent(option, k -> {
                    // System.out.println("Adding " + k);
                    return 0;
                });
                options.put(option, options.get(option) + 1);
            }
        }

        for (int i = preamble; i < data.size(); i++) {
            var value = data.get(i);
            // System.out.println("Processing " + value);
            // System.out.println(options);
            if (!options.containsKey(value)) {
                return value;
            }

            var first = data.get(i - preamble);
            for (int j = 1; j < preamble; j++) {
                var second = data.get(i - preamble + j);
                var sum = first + second;
                if (options.get(sum) == 1) {
                    options.remove(sum);
                    // System.out.println("Removed " + sum);
                } else {
                    options.put(sum, options.get(sum) - 1);
                }

                var option = value + second;
                options.computeIfAbsent(option, k -> {
                    // System.out.println("Adding " + k);
                    return 0;
                });
                options.put(option, options.get(option) + 1);
            }
        }

        throw new IllegalArgumentException("Invalid xmas");
    }

    public long encryptionWeakness() {
        var firstInvalidNumber = firstInvalidNumber();

        int startIndex = 0;
        long currentSum = 0;
        for (var i = 0; i < data.size(); i++) {
            var value = data.get(i);
            currentSum += value;

            while (currentSum > firstInvalidNumber) {
                currentSum -= data.get(startIndex++);
            }

            if (currentSum == firstInvalidNumber) {
                var subList = data.subList(startIndex, i);
                var min = subList.stream().min(Long::compareTo).orElseThrow();
                var max = subList.stream().max(Long::compareTo).orElseThrow();
                return min + max;
            }
        }

        return -1;
    }
}
