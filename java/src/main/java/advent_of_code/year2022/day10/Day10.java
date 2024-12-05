package advent_of_code.year2022.day10;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

class Day10 {

    int part1(List<String> lines) {
        List<Instruction> instructions = parse(lines);

        int x = 1;
        int result = 0;
        for (int i = 1; i < instructions.size() + 1; i++) {
            var instruction = instructions.get(i - 1);
            x = instruction.apply(x);

            if ((i % 40) == 20) {
                result += x * i;
            }
        }

        return result;
    }

    private static List<Instruction> parse(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction.Noop());
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            instructions.add(new Instruction.Noop());
            if (line.startsWith("addx")) {
                var split = line.split(" ");
                var value = Integer.parseInt(split[split.length - 1]);
                instructions.add(new Instruction.AddX(value));
            }
        }
        return instructions;
    }

    Long part2(List<String> lines) {
        List<Instruction> instructions = parse(lines);

        int x = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            x = instruction.apply(x);

            if ((i % 40) == 0) {
                sb.append("\n");
            }
            if ((i % 40) <= x + 1 && (i % 40) >= x - 1) {
                sb.append("###");
            } else {
                sb.append("...");
            }
        }

        System.out.println(sb.toString());

        return null;
    }
}
