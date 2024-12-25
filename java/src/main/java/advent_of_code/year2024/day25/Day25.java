package advent_of_code.year2024.day25;

import advent_of_code.utils.CollectionUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day25 {

    Long part1(List<String> lines) {
        Set<Key> keys = new HashSet<>();
        Set<Lock> locks = new HashSet<>();
        for (int i = 0; i < lines.size(); i+=8) {
            boolean isKey = lines.get(i).startsWith(".");
            if (isKey) {
                keys.add(Key.of(lines.subList(i, i + 7)));
            } else {
                locks.add(Lock.of(lines.subList(i, i + 7)));
            }
        }
        return CollectionUtils.pairOf(keys, locks).stream().filter(pair -> {
            var key = pair.first();
            var lock = pair.second();

            return key.fit(lock);
        }).count();
    }
}
