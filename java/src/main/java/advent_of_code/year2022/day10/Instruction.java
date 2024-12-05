package advent_of_code.year2022.day10;

public interface Instruction {
    int apply(int previousValue);

    record Noop() implements Instruction {
        @Override
        public int apply(int previousValue) {
            return previousValue;
        }
    }

    final class AddX implements Instruction {

        private final int value;

        public AddX(int value) {
            this.value = value;
        }

        @Override
        public int apply(int previousValue) {
            //            System.out.println("Add " + value);
            return previousValue + value;
        }
    }
}
