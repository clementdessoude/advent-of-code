package advent_of_code.year2020.day13;

import advent_of_code.utils.ModularInverse;
import advent_of_code.utils.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day13 {

    Long part1(List<String> lines) {
        var shortestDepartTime = Long.parseLong(lines.getFirst());
        var busIds = Arrays.stream(lines.getLast().split(","))
            .filter(s -> !s.equals("x"))
            .map(Long::parseLong)
            .toList();

        long minBusId = busIds.getFirst();
        long minWait = wait(shortestDepartTime, minBusId);
        for (int i = 1; i < busIds.size(); i++) {
            var busId = busIds.get(i);
            var wait = wait(shortestDepartTime, busId);
            if (wait < minWait) {
                minWait = wait;
                minBusId = busId;
            }
        }

        return minWait * minBusId;
    }

    long wait(long shortestDepartTime, long busId) {
        return ((-shortestDepartTime) % busId) + busId;
    }

    Long part2(List<String> lines) {
        List<Pair<Long, Long>> values = new ArrayList<>();
        var busSchedule = lines.getLast().split(",");
        for (int i = 0; i < busSchedule.length; i++) {
            var busId = busSchedule[i];
            if (busId.equals("x")) {
                continue;
            }
            var bus = Long.parseLong(busId);
            values.add(new Pair<>(bus - i, bus));
        }

        return ModularInverse.solve(values);
    }
}
