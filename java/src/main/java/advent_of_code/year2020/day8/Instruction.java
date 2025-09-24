package advent_of_code.year2020.day8;

sealed interface Instruction permits Accumulator, Noop, Jump {}

record Accumulator(int value) implements Instruction {}

record Noop() implements Instruction {}

record Jump(int value) implements Instruction {}
