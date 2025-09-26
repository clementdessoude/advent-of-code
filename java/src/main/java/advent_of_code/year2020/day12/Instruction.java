package advent_of_code.year2020.day12;

sealed interface Instruction permits North, South, East, West, Forward, Left, Right {
}

record North(int value) implements Instruction {}
record South(int value) implements Instruction {}
record East(int value) implements Instruction {}
record West(int value) implements Instruction {}
record Forward(int value) implements Instruction {}
record Left(int value) implements Instruction {}
record Right(int value) implements Instruction {}