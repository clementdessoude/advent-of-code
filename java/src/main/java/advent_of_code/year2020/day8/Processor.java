package advent_of_code.year2020.day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Processor {

    private final List<Instruction> instructions;
    private long accumulator = 0;

    Processor() {
        instructions = new ArrayList<>();
    }

    Processor(List<Instruction> instructions) {
        this.instructions = List.copyOf(instructions);
    }

    Processor add(Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    boolean run() {
        Set<Integer> visited = new HashSet<>();
        var cursor = 0;
        while (!visited.contains(cursor)) {
            visited.add(cursor);
            var instruction = instructions.get(cursor);
            switch (instruction) {
                case Accumulator(int value) -> {
                    accumulator += value;
                    cursor += 1;
                }
                case Noop ignored -> cursor += 1;
                case Jump(int value) -> {
                    cursor += value;
                }
            }

            if (cursor == instructions.size()) {
                return true;
            }
        }

        return false;
    }

    long accumulator() {
        return accumulator;
    }

    Processor debug() {
        List<Processor> processors = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            if (instruction instanceof Jump) {
                processors.add(this.noopAt(i));
            }
        }

        for (Processor processor : processors) {
            if (processor.run()) {
                return processor;
            }
        }

        throw new IllegalStateException("Wrong");
    }

    Processor noopAt(int i) {
        var newInstructions = new ArrayList<>(instructions);
        newInstructions.set(i, new Noop());
        return new Processor(newInstructions);
    }
}
