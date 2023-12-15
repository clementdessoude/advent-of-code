
package adventOfCode.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {

    public Long part1(List<String> lines) {
        String input = lines.get(0);

        return Arrays.stream(input.split(",")).mapToLong(Day15::hash).sum();
    }

    static int hash(String input) {
        int currentValue = 0;
        for (int i = 0; i < input.length(); i++) {
            currentValue += (int) input.charAt(i);
            currentValue *= 17;
            currentValue %= 256;
        }

        return currentValue;
    }

    record Lens(String label, short focal) {
        public long focusingPower(int lensIndex) {
            return (long) focal * (lensIndex + 1);
        }
    }
    record Box(List<Lens> lenses) {
        Box() {
            this(List.of());
        }

        public Box remove(String label) {
            return new Box(lenses.stream().filter(lens -> !label.equals(lens.label())).toList());
        }

        public Box add(String label, short focal) {
            if (lenses.stream().anyMatch(lens -> lens.label.equals(label))) {
                return new Box(lenses
                                   .stream()
                                   .map(lens -> {
                                       if (lens.label.equals(label)) {
                                           return new Lens(label, focal);
                                       }
                                       return lens;
                                   })
                                   .toList());
            }

            var updated = new ArrayList<>(lenses);
            updated.add(new Lens(label, focal));

            return new Box(updated);
        }

        public long focusingPower(int boxIndex) {
            return (1 + boxIndex) * IntStream.range(0, lenses.size())
                .mapToLong(i -> lenses.get(i).focusingPower(i))
                .sum();
        }
    }

    record Step(List<Box> boxes) {
        Step() {
            this(IntStream.range(0, 256).mapToObj(i -> new Box()).toList());
        }

        public Step next(String instruction) {
            if (instruction.contains("-")) {
                String label = instruction.substring(0, instruction.length() - 1);
                List<Box> updated = removeLens(label);

                return new Step(updated);
            }

            var split = instruction.split("=");
            var label = split[0];
            var focal = Short.parseShort(split[1]);

            List<Box> updated = addLens(label, focal);

            return new Step(updated);
        }

        private List<Box> addLens(String label, short focal) {
            int boxIndex = Day15.hash(label);

            List<Box> updated = new ArrayList<>(boxes);

            Box box = updated.get(boxIndex);
            Box updatedBox = box.add(label, focal);
            updated.set(boxIndex, updatedBox);

            return updated;
        }

        private List<Box> removeLens(String label) {
            int boxIndex = Day15.hash(label);

            List<Box> updated = new ArrayList<>(boxes);

            Box box = updated.get(boxIndex);
            Box updatedBox = box.remove(label);
            updated.set(boxIndex, updatedBox);

            return updated;
        }

        public Long focusingPower() {
            return IntStream
                .range(0, boxes.size())
                .mapToLong(i -> boxes.get(i).focusingPower(i))
                .sum();
        }
    }

    public Long part2(List<String> lines) {
        String input = lines.get(0);
        List<String> instructions = Arrays.stream(input.split(",")).toList();

        Step step = new Step();
        for (String instruction: instructions) {
            step = step.next(instruction);
        }

        return step.focusingPower();
    }
}

