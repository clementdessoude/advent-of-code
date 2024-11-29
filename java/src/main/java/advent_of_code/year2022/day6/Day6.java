package advent_of_code.year2022.day6;

import java.util.stream.Collectors;

public class Day6 {

    public Long part1(String input) {
        int packetLength = 4;
        return firstPacketWithLengthIn(input, packetLength);
    }

    private long firstPacketWithLengthIn(String input, int packetLength) {
        for (int i = 0; i < input.length() - packetLength; i++) {
            String packet = input.substring(i, i + packetLength);
            if (!hasDuplicateLetters(packet)) {
                return (long) (i + packetLength);
            }
        }
        throw new IllegalArgumentException("");
    }

    private boolean hasDuplicateLetters(String input) {
        var differentCharactersCount = input.chars().boxed().collect(Collectors.toSet()).size();

        return input.length() != differentCharactersCount;
    }

    public Long part2(String input) {
        return firstPacketWithLengthIn(input, 14);
    }
}
