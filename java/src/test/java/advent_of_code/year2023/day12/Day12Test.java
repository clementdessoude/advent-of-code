
package advent_of_code.year2023.day12;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

class Day12Test {

    Day12 day = new Day12();

    @ParameterizedTest
    @MethodSource("providePart1Examples")
    void should_solve_example_lines(String input, long count) {
        List<String> lines = List.of(input);

        var result = day.part1(lines);

        assertThat(result).isEqualTo(count);
    }

    private static Stream<Arguments> providePart1Examples() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 4),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 1),
                Arguments.of("????.######..#####. 1,6,5", 4),
                Arguments.of("?###???????? 3,2,1", 10),
                Arguments.of(".??????????#.??.? 4,1,1,1,1", 32)
        );
    }

    @Test
    void should_solve_example() {
        List<String> lines = getLines("day12/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(21);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("day12/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(7084);
    }

    @ParameterizedTest
    @MethodSource("providePart2Examples")
    void should_solve_part2_example_lines(String input, long count) {
        List<String> lines = List.of(input);

        var result = day.part2(lines);

        assertThat(result).isEqualTo(count);
    }

    private static Stream<Arguments> providePart2Examples() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 16384),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 16),
                Arguments.of("????.######..#####. 1,6,5", 2500),
                Arguments.of("?###???????? 3,2,1", 506250),
                Arguments.of(".??????????#.??.? 4,1,1,1,1", 1883226112)
        );
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines("day12/example.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(525152);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("day12/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(8414003326821L);
    }

    @Test
    void should_print() {
        List<String> lines = getLines("day12/result.txt");

        List<Integer> list = lines.stream()
                                  .filter(s -> !StringUtils.isBlank(s))
                                  .map(s -> s.split(" ")[1].replace(":", ""))
                                  .map(Integer::parseInt)
                                  .distinct()
                                  .sorted()
                                  .toList();

        list.forEach(System.out::println);

        System.out.println((double) list.size() / 1000d);
    }
}

