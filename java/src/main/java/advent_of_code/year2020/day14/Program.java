package advent_of_code.year2020.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Program {

    private final List<Instruction> instructions;

    private long positiveMask;
    private long negativeMask;
    private final Map<Integer, Long> memory = new HashMap<>();

    public Program(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Long run() {
        instructions.forEach(this::run);
        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    private void run(Instruction instruction) {
        switch (instruction) {
            case Assignment assignment -> {
                memory.put(
                    assignment.memoryAddress(),
                    (assignment.value() & negativeMask) | positiveMask
                );
            }
            case Mask mask -> {
                positiveMask = Long.parseLong(mask.mask().replaceAll("X", "0"), 2);
                negativeMask = Long.parseLong(mask.mask().replaceAll("X", "1"), 2);
            }
        }
    }
}
