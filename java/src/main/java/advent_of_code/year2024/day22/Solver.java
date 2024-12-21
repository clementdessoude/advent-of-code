package advent_of_code.year2024.day22;

import java.util.*;
import java.util.function.Function;

final class Solver {

    private static final Function<Long, Long> firstOperation = operate(Solver::multiplyBy64);
    private static final Function<Long, Long> secondOperation = operate(Solver::divideBy32);
    private static final Function<Long, Long> thirdOperation = operate(Solver::multiplyBy2048);
    private final Map<List<Long>, Long> countBySequence;

    public Solver() {
        this.countBySequence = new HashMap<>();
    }

    long maxBananas(List<Long> inputs) {
        inputs.forEach(this::process);
        var sorted = countBySequence
            .entrySet()
            .stream()
            .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
            .limit(10)
            .toList();
        System.out.println(sorted);
        return countBySequence.values().stream().max(Long::compareTo).orElse(0L);
    }

    private void process(Long input) {
        var result = input;
        List<Long> prices = new ArrayList<>();
        prices.add(result % 10);
        for (int i = 0; i < 2000; i++) {
            result = evolve(result);
            prices.add(result % 10);
        }

        List<Long> differences = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            differences.add(prices.get(i) - prices.get(i - 1));
        }

        Set<List<Long>> visited = new HashSet<>();
        for (int i = 4; i < differences.size(); i++) {
            List<Long> sequence = differences.subList(i-4, i);
            if (!visited.contains(sequence)) {
                visited.add(sequence);
                var price = prices.get(i);
                countBySequence.compute(
                    sequence,
                    (k,v) -> (v == null ? 0 : v) + price
                );
            }
        }
    }

    static long solve(long input) {
        var result = input;
        for (int i = 0; i < 2000; i++) {
            result = evolve(result);
        }
        return result;
    }

    static long evolve(long input) {
        return firstOperation.andThen(secondOperation).andThen(thirdOperation).apply(input);
    }

    private static long multiplyBy64(long input) {
        return input << 6;
    }

    private static long divideBy32(long input) {
        return input >> 5;
    }

    private static long multiplyBy2048(long input) {
        return input << 11;
    }

    private static Function<Long, Long> operate(Function<Long, Long> operation) {
        return (input) -> prune(mix(input, operation.apply(input)));
    }

    private static long mix(long input, long value) {
        return input ^ value;
    }

    private static long prune(long input) {
        return input & 16777215;
    }
}
