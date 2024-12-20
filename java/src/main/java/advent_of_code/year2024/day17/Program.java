package advent_of_code.year2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final class Program {

    private Long registerA;
    private Long registerB;
    private Long registerC;
    private int instructionPointer;
    private int cycle = 0;
    private final List<Long> instructions;

    Program(List<Long> instructions, Long registerA, Long registerB, Long registerC) {
        this.instructions = instructions;
        this.registerC = registerC;
        this.registerB = registerB;
        this.registerA = registerA;
        instructionPointer = 0;
    }

    public String output() {
        List<Long> outputs = new ArrayList<>();
        while (instructionPointer < instructions.size()) {
            process(instructionPointer, outputs, null);
        }

        return outputs.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private void process(int instructionPointer, List<Long> outputs, List<Long> expectedOut) {
        var opcode = instructions.get(instructionPointer);
        var operand = instructions.get(instructionPointer + 1);
        if (opcode == 0L) {
            adv(operand);
        } else if (opcode == 1L) {
            bxl(operand);
        } else if (opcode == 2L) {
            bst(operand);
        } else if (opcode == 3L) {
            jnz(operand);
        } else if (opcode == 4L) {
            bxc();
        } else if (opcode == 5L) {
            out(outputs, operand);
        } else if (opcode == 6L) {
            bdv(operand);
        } else if (opcode == 7L) {
            cdv(operand);
        } else {
            throw new IllegalStateException("Unknown opcode: " + opcode);
        }

        if (opcode != 3L) {
            this.instructionPointer += 2;
        }
    }

    private void cdv(Long operand) {
        registerC = registerA >> comboOperand(operand);
    }

    private void bdv(Long operand) {
        registerB = registerA >> comboOperand(operand);
    }

    private void out(List<Long> outputs, Long operand) {
        outputs.add(comboOperand(operand) % 8);
    }

    private void bxc() {
        registerB = registerB ^ registerC;
    }

    private void jnz(Long operand) {
        if (registerA == 0L) {
            instructionPointer += 2;
            return;
        }

        this.instructionPointer = Math.toIntExact(operand);
    }

    private void bst(Long operand) {
        registerB = comboOperand(operand) % 8;
    }

    private void bxl(Long operand) {
        registerB = registerB ^ operand;
    }

    private void adv(long operand) {
        registerA = registerA >> comboOperand(operand);
    }

    private Long comboOperand(long operand) {
        return switch ((int) operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new IllegalStateException("Unexpected value: " + operand);
        };
    }

    public boolean isRecursive(List<Long> out) {
        List<Long> outputs = new ArrayList<>();

        var keep = registerA;
        while (instructionPointer < instructions.size()) {
            cycle++;
            process(instructionPointer, outputs, out);
            if (!outputs.isEmpty() && !outputs.getLast().equals(out.get(outputs.size() - 1))) {
                return false;
            }
        }

        return outputs.equals(out);
    }
}
