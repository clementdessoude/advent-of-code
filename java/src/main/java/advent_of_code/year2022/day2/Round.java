package advent_of_code.year2022.day2;

record Round(Move opponent, Move yours) {
    long score() {
        return moveScore() + outcomeScore();
    }

    private long moveScore() {
        return switch (yours) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };
    }

    private long outcomeScore() {
        return switch (outcome()) {
            case WIN -> 6;
            case LOSE -> 0;
            case DRAW -> 3;
        };
    }

    private Outcome outcome() {
        if (opponent == yours) {
            return Outcome.DRAW;
        }

        return switch (yours) {
            case ROCK -> opponent == Move.PAPER ? Outcome.LOSE : Outcome.WIN;
            case PAPER -> opponent == Move.SCISSORS ? Outcome.LOSE : Outcome.WIN;
            case SCISSORS -> opponent == Move.ROCK ? Outcome.LOSE : Outcome.WIN;
        };
    }
}
