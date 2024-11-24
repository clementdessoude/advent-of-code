package advent_of_code.year2022.day2;

import java.util.List;

public class Day2 {

    public Long part1(List<String> lines) {
        var rounds = parse(lines);

        return rounds.stream().mapToLong(Round::score).sum();
    }

    public Long part2(List<String> lines) {
        return null;
    }

    private List<Round> parse(List<String> lines) {
        return lines.stream().map(s -> s.split(" ")).map(split -> new Round(opponent(split[0]), yours(split[1]))).toList();
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
}
