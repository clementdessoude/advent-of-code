package advent_of_code.year2023.day4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {
    public Integer part1(List<String> lines) {
        return lines
            .stream()
            .map(Day4::parse)
            .mapToInt(Card::score)
            .sum();
    }

    record Card(Set<Integer> winnings, List<Integer> owned, int count) {

        Card(Set<Integer> winnings, List<Integer> owned) {
            this(winnings, owned, 1);
        }

        public int score() {
            var winningOwned = matchingCardsCount();

            return winningOwned > 0 ? (int) Math.pow(2, winningOwned - 1) : 0;
        }

        public long matchingCardsCount() {
            return owned.stream().filter(winnings::contains).count();
        }

        public Card addCount(int i) {
            return new Card(winnings, owned, count + i);
        }
    }

    private static Card parse(String row) {
        String numbers = row.split(":")[1];
        String[] split = numbers.split("\\|");
        String winnings = split[0];
        String owned = split[1];

        return new Card(new HashSet<>(parseNumbers(winnings)), parseNumbers(owned));
    }

    private static List<Integer> parseNumbers(String row) {
        return Arrays.stream(row.split(" "))
                     .filter(s -> !s.isBlank())
                     .map(Integer::parseInt)
                     .toList();
    }

    public Integer part2(List<String> lines) {
        var cards = lines
            .stream()
            .map(Day4::parse)
            .collect(Collectors.toList());

        for (int i = 0; i < lines.size(); i++) {
            var current = cards.get(i);
            var matchingCardsCount = current.matchingCardsCount();

            for (int j = i + 1; j < i + matchingCardsCount + 1; j++) {
                cards.set(j, cards.get(j).addCount(current.count));
            }
        }

        return cards.stream().mapToInt(Card::count).sum();
    }
}
