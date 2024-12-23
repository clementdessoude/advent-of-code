package advent_of_code.year2022.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

final class Crates {

    private final List<Stack<String>> crates;

    public Crates(List<String> lines, int cratesCount, int splitIndex) {
        List<Stack<String>> crates = new ArrayList<>();
        for (int i = 0; i < cratesCount; i++) {
            crates.add(new Stack<>());
        }
        for (int i = splitIndex - 2; i >= 0; i--) {
            String line = lines.get(i);
            for (int crateIndex = 0; crateIndex < cratesCount; crateIndex++) {
                if (crateIndex * 4 + 3 > line.length()) {
                    break;
                }
                var lineCrateAtIndex = line.substring(crateIndex * 4 + 1, crateIndex * 4 + 2);
                if (!lineCrateAtIndex.isBlank()) {
                    crates.get(crateIndex).add(lineCrateAtIndex);
                }
            }
        }
        this.crates = Collections.unmodifiableList(crates);
    }

    public void apply(Instruction instruction) {
        for (int i = 0; i < instruction.quantity(); i++) {
            crates
                .get(instruction.destination() - 1)
                .add(crates.get(instruction.source() - 1).pop());
        }
    }

    public void apply(List<Instruction> instructions) {
        instructions.forEach(this::apply);
    }

    public String top() {
        return crates.stream().map(List::getLast).collect(Collectors.joining());
    }

    public void applyKeepingOrder(List<Instruction> instructions) {
        instructions.forEach(this::applyKeepingOrder);
    }

    private void applyKeepingOrder(Instruction instruction) {
        Stack<String> tmp = new Stack<>();
        for (int i = 0; i < instruction.quantity(); i++) {
            tmp.add(crates.get(instruction.source() - 1).pop());
        }

        for (int i = 0; i < instruction.quantity(); i++) {
            crates.get(instruction.destination() - 1).add(tmp.pop());
        }
    }
}
