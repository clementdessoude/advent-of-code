package advent_of_code.year2020.day14;

sealed interface Instruction {}

record Assignment(int memoryAddress, long value) implements Instruction {}

record Mask(String mask) implements Instruction {}
