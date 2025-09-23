package advent_of_code.year2020.day5;

class BoardingPass {

    private final String boardingPass;

    public BoardingPass(String boardingPass) {
        this.boardingPass = boardingPass;
    }

    public long seatId() {
        return 8 * rowId() + columnId();
    }

    long rowId() {
        return dichotomy(boardingPassRowId(), 'B');
    }

    long columnId() {
        return dichotomy(boardingPassColumnId(), 'R');
    }

    private static long dichotomy(String identifier, char upperHalfIndicator) {
        long result = 0;
        int length = identifier.length();
        for (int i = 0; i < length; i++) {
            char c = identifier.charAt(i);
            if (c == upperHalfIndicator) {
                result += (long) Math.pow(2, length - i - 1);
            }
        }

        return result;
    }

    private String boardingPassRowId() {
        return boardingPass.substring(0, 7);
    }

    private String boardingPassColumnId() {
        return boardingPass.substring(7);
    }
}
