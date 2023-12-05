package adventOfCode.day5;

import adventOfCode.day5.part1.Part1Solver;
import adventOfCode.day5.part2.Part2Solver;
import java.util.*;

public class Day5 {
    public Long part1(List<String> lines) {
        return new Part1Solver(lines).solve();
    }

    public Long part2(List<String> lines) {
        return new Part2Solver(lines).solve();
    }
}
