package advent_of_code.year2022.day2;

enum Move {
    ROCK,
    PAPER,
    SCISSORS;

    public Move losesAgainst() {
        return switch (this) {
            case ROCK -> PAPER;
            case PAPER -> SCISSORS;
            case SCISSORS -> ROCK;
        };
    }

    public Move winsAgainst() {
        return switch (this) {
            case ROCK -> SCISSORS;
            case PAPER -> ROCK;
            case SCISSORS -> PAPER;
        };
    }
}
