package adventOfCode.day7;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {

    public Long part1(List<String> lines) {
        return solve(lines, true);
    }

    private static long solve(List<String> lines, boolean isPart1) {
        List<Hand> hands = parse(lines)
            .stream()
            .sorted(
                Comparator
                        .comparing((Hand hand) -> hand.rank(isPart1))
                        .thenComparing((hand1, other) -> hand1.compare(other,
                                                                       isPart1
                        ))
            )
            .toList();

        return IntStream
            .range(0, hands.size())
            .mapToLong(i -> hands.get(i).bid() * (i + 1))
            .sum();
    }

    public Long part2(List<String> lines) {
        return solve(lines, false);
    }

    record Card(Character c) {

        public int rank(boolean part1) {
            return part1 ? rankPart1() : rankPart2();
        }

        public int rankPart1() {
            return switch (c) {
                case '2' -> 1;
                case '3' -> 2;
                case '4' -> 3;
                case '5' -> 4;
                case '6' -> 5;
                case '7' -> 6;
                case '8' -> 7;
                case '9' -> 8;
                case 'T' -> 9;
                case 'J' -> 10;
                case 'Q' -> 11;
                case 'K' -> 12;
                case 'A' -> 13;
                default ->
                    throw new IllegalStateException("Unexpected value: " + c);
            };
        }

        private int rankPart2() {
            return switch (c) {
                case 'J' -> 0;
                default -> rankPart1();
            };
        }

        public boolean isJoker() {
            return c == 'J';
        }
    }
    record Hand(List<Card> cards, long bid) {
        short rank(boolean isPart1) {
            Map<Card, Long> result = cards().stream()
               .collect(Collectors.groupingBy(
                   Function.identity(),
                   Collectors.counting()
               ));
            List<Map.Entry<Card, Long>> list = result
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .toList();

            return isPart1 ? rankPart1(list) : rankPart2(list);
        }

        private static short rankPart1(List<Map.Entry<Card, Long>> list) {
            int cardNumber = list.size() - 1;
            if (list.get(cardNumber).getValue() == 5) {
                return 7;
            }

            if (list.get(cardNumber).getValue() == 4) {
                return 6;
            }

            if (list.get(cardNumber).getValue() == 3 && list
                .get(cardNumber - 1).getValue() == 2) {
                return 5;
            }

            if (list.get(cardNumber).getValue() == 3) {
                return 4;
            }

            if (list.get(cardNumber).getValue() == 2 && list.get(cardNumber -1).getValue() == 2) {
                return 3;
            }

            if (list.get(cardNumber).getValue() == 2) {
                return 2;
            }

            return 1;
        }

        private static short rankPart2(List<Map.Entry<Card, Long>> list) {
            int cardNumber = list.size() - 1;
            if (list.get(cardNumber).getValue() == 5) {
                return 7;
            }

            if (list.get(cardNumber).getValue() == 4) {
                if (list.get(cardNumber).getKey().isJoker() || list.get(cardNumber - 1).getKey().isJoker()) {
                    return 7;
                }
                return 6;
            }

            if (list.get(cardNumber).getValue() == 3 && list.get(cardNumber - 1).getValue() == 2) {
                if (list.get(cardNumber).getKey().isJoker() || list.get(cardNumber - 1).getKey().isJoker()) {
                    return 7;
                }
                return 5;
            }

            if (list.get(cardNumber).getValue() == 3) {
                if (list.get(cardNumber).getKey().isJoker() || list.get(cardNumber - 1).getKey().isJoker() || list.get(cardNumber - 2).getKey().isJoker()) {
                    return 6;
                }
                return 4;
            }

            if (list.get(cardNumber).getValue() == 2 && list.get(cardNumber -1).getValue() == 2) {
                if (list.get(cardNumber).getKey().isJoker() || list.get(cardNumber - 1).getKey().isJoker()) {
                    return 6;
                }
                if (list.get(cardNumber - 2).getKey().isJoker()) {
                    return 5;
                }
                return 3;
            }

            if (list.get(cardNumber).getValue() == 2) {
                if (
                    list.get(cardNumber).getKey().isJoker() ||
                    list.get(cardNumber - 1).getKey().isJoker() ||
                    list.get(cardNumber - 2).getKey().isJoker() ||
                    list.get(cardNumber - 3).getKey().isJoker()
                ) {
                    return 4;
                }
                return 2;
            }

            if (
                list.get(cardNumber).getKey().isJoker() ||
                    list.get(cardNumber - 1).getKey().isJoker() ||
                    list.get(cardNumber - 2).getKey().isJoker() ||
                    list.get(cardNumber - 3).getKey().isJoker() ||
                    list.get(cardNumber - 4).getKey().isJoker()
            ) {
                return 2;
            }

            return 1;
        }

        int compare(Hand other, boolean isPart1) {
            for (int i = 0; i < cards().size(); i++) {
                if (cards.get(i).rank(isPart1) == other.cards.get(i).rank(isPart1)) {
                    continue;
                }
                if (cards.get(i).rank(isPart1) < other.cards.get(i).rank(isPart1)) {
                    return -1;
                }

                return 1;
            }
            return 1;
        }
    }

    private static List<Hand> parse(List<String> lines) {
        return lines
            .stream()
            .map(Day7::parse)
            .toList();
    }

    private static Hand parse(String row) {
        String[] split = row.split(" ");
        var bid = Long.parseLong(split[1]);

        var cards = split[0]
            .chars()
            .mapToObj(c -> (char) c)
            .map(Card::new)
            .toList();

        return new Hand(cards, bid);
    }
}
