package advent_of_code.year2022.day2;

import java.util.List;

public class Day2 {

    public Long part1(List<String> lines) {
        var rounds = lines
            .stream()
            .map(s -> s.split(" "))
            .map(split -> new Round(opponent(split[0]), yours(split[1])))
            .toList();

        return rounds.stream().mapToLong(Round::score).sum();
    }

    public Long part2(List<String> lines) {
        var rounds = lines
            .stream()
            .map(s -> s.split(" "))
            .map(split -> {
                Move opponent = opponent(split[0]);
                Outcome outcome = parseOutcome(split[1]);
                return new Round(opponent, fromOpponentAndOutcome(opponent, outcome));
            })
            .toList();

        return rounds.stream().mapToLong(Round::score).sum();
    }

    private Move opponent(String s) {
        if ("A".equals(s)) {
            return Move.ROCK;
        }

        if ("B".equals(s)) {
            return Move.PAPER;
        }

        return Move.SCISSORS;
    }

    private Move yours(String s) {
        if ("X".equals(s)) {
            return Move.ROCK;
        }

        if ("Y".equals(s)) {
            return Move.PAPER;
        }

        return Move.SCISSORS;
    }

    private Outcome parseOutcome(String s) {
        if ("X".equals(s)) {
            return Outcome.LOSE;
        }

        if ("Y".equals(s)) {
            return Outcome.DRAW;
        }

        return Outcome.WIN;
    }

    private Move fromOpponentAndOutcome(Move move, Outcome outcome) {
        return switch (outcome) {
            case LOSE -> move.winsAgainst();
            case WIN -> move.losesAgainst();
            case DRAW -> move;
        };
    }
}
