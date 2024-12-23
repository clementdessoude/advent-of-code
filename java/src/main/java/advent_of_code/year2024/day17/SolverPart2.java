package advent_of_code.year2024.day17;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class SolverPart2 {

    private final List<Long> instructions;

    SolverPart2(List<Long> instructions) {
        this.instructions = instructions;
    }

    public long solvePart2() {
        var registerAOptions = Set.of(0L);
        for (int i = instructions.size() - 1; i >= 0; i--) {
            registerAOptions = reverseEngineer(registerAOptions, instructions.get(i));
        }
        return registerAOptions.stream().min(Long::compareTo).orElseThrow();
    }

    public Set<Long> reverseEngineer(Set<Long> possibilities, long expected) {
        Set<Long> options = new HashSet<>();
        for (long possibility : possibilities) {
            for (long i = 0; i < 8; i++) {
                var registerA = (possibility << 3) + i;
                long n = (registerA & 7) ^ 5;
                long m = registerA >> n;
                var registerB = (n ^ m) ^ 6;

                if ((registerB & 7) == expected) {
                    options.add(registerA);
                }
            }
        }

        return options;
    }
}
