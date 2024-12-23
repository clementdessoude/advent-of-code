package advent_of_code.year2024.day17;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day17Test {

    Day17 day = new Day17();

    @Test
    void should_solve_example() {
        List<String> lines = getLines("year2024/day17/example.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo("4,6,3,5,6,3,5,2,1,0");
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines("year2024/day17/input.txt");

        var result = day.part1(lines);

        assertThat(result).isEqualTo("3,4,3,1,7,6,5,6,0");
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines("year2024/day17/input.txt");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(109_019_930_331_546L);
    }

    @Test
    void test() {
        List<String> lines = List.of(
            "Register A: 109019930331546",
            "Register B: 0",
            "Register C: 0",
            "",
            "Program: 2,4,1,5,7,5,4,5,0,3,1,6,5,5,3,0"
        );

        var result = day.part1(lines);

        assertThat(result).isEqualTo("2,4,1,5,7,5,4,5,0,3,1,6,5,5,3,0");
    }
}
