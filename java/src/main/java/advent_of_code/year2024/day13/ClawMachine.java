package advent_of_code.year2024.day13;

import advent_of_code.utils.Pair;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ClawMachine {

    private static final Pattern BUTTON_PATTERN = Pattern.compile(
        "^Button .: X\\+(?<x>\\d+), Y\\+(?<y>\\d+)$"
    );
    private static final Pattern PRIZE_PATTERN = Pattern.compile(
        "^Prize: X=(?<x>\\d+), Y=(?<y>\\d+)$"
    );

    private final Pair<Long, Long> buttonA;
    private final Pair<Long, Long> buttonB;
    private final Pair<Long, Long> prize;

    public ClawMachine(String buttonA, String buttonB, String prize, boolean isPart1) {
        this.buttonA = parse(buttonA, BUTTON_PATTERN, 0L);
        this.buttonB = parse(buttonB, BUTTON_PATTERN, 0L);
        this.prize = parse(prize, PRIZE_PATTERN, isPart1 ? 0 : 10000000000000L);
    }

    long resolve() {
        return findWinningCombination()
            .map(combination -> 3 * combination.first() + combination.second())
            .orElse(0L);
    }

    private Optional<Pair<Long, Long>> findWinningCombination() {
        long one = buttonA.second() * prize.first() - buttonA.first() * prize.second();
        long denominator = buttonA.second() * buttonB.first() - buttonA.first() * buttonB.second();
        long second = buttonB.first() * prize.second() - buttonB.second() * prize.first();

        if (one % denominator != 0 || second % denominator != 0) {
            return Optional.empty();
        }

        return Optional.of(new Pair<>(second / denominator, one / denominator));
    }

    private Pair<Long, Long> parse(String input, Pattern pattern, Long adjust) {
        Matcher matcher = pattern.matcher(input);
        var _ = matcher.matches();

        var x = adjust + Long.parseLong(matcher.group("x"));
        var y = adjust + Long.parseLong(matcher.group("y"));

        return new Pair<>(x, y);
    }
}
