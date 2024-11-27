package advent_of_code.year2022.day5;

import java.util.regex.Pattern;

record Instruction(int source, int destination, int quantity) {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile(
        "^move (?<quantity>(\\d*)) from (?<source>(\\d*)) to (?<destination>(\\d*))$"
    );

    public static Instruction of(String input) {
        var matcher = INSTRUCTION_PATTERN.matcher(input);

        try {
            matcher.matches();

            return new Instruction(
                Integer.parseInt(matcher.group("source")),
                Integer.parseInt(matcher.group("destination")),
                Integer.parseInt(matcher.group("quantity"))
            );
        } catch (IllegalStateException e) {
            System.out.printf("Error parsing '%s'%n", input);
            throw new RuntimeException(e);
        }
    }
}
