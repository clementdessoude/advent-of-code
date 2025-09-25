package advent_of_code.year2020.day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Day10 {

    Long part1(List<String> lines) {
        var adapters = lines.stream().map(Long::parseLong).sorted().collect(Collectors.toList());
        adapters.addFirst(0L);
        adapters.add(adapters.getLast() + 3);

        long oneJoltJumpCount = 0;
        long threeJoltJumpCount = 0;
        for (var i = 0; i < adapters.size() - 1; i++) {
            var previous = adapters.get(i);
            var next = adapters.get(i + 1);
            if (previous == next - 1) {
                oneJoltJumpCount++;
            }
            if (previous == next - 3) {
                threeJoltJumpCount++;
            }
        }

        return oneJoltJumpCount * threeJoltJumpCount;
    }

    Long part2(List<String> lines) {
        Map<Long, Long> cache = new HashMap<>();

        var adapters = lines.stream().map(Long::parseLong).sorted().collect(Collectors.toList());
        adapters.addFirst(0L);
        adapters.add(adapters.getLast() + 3);

        long l = numberOfArrangements(adapters, cache);
        return l;
    }

    private long numberOfArrangements(List<Long> adapters, Map<Long, Long> cache) {
        var first = adapters.getFirst();
        if (cache.containsKey(first)) {
            return cache.get(first);
        }

        if (adapters.size() == 2) {
            return 1;
        }

        long result = 0;
        int i = 1;
        while (i <= adapters.size() - 1) {
            var value = adapters.get(i);
            if (first + 3 < value) {
                break;
            }

            var numberOfArrangements = numberOfArrangements(
                adapters.subList(i, adapters.size()),
                cache
            );
            result += numberOfArrangements;
            i++;
        }

        cache.put(first, result);
        return result;
    }
}
