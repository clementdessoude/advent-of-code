#!/bin/bash
cd $(dirname $0)

year=2022
# Source
mkdir -p "src/main/java/advent_of_code/year$year/day$1"
echo "
package advent_of_code.year$year.day$1;

import java.util.List;

public class Day$1 {

    public Long part1(List<String> lines) {
        return null;
    }

    public Long part2(List<String> lines) {
        return null;
    }
}
" >> "src/main/java/advent_of_code/year$year/day$1/Day$1.java"

sed -i '' "s/XX/$1/g" "src/main/java/advent_of_code/year$year/day$1/Day$1.java"

# Tests
mkdir -p "src/test/java/advent_of_code/year$year/day$1"
echo "
package advent_of_code.year$year.day$1;

import static advent_of_code.TestUtils.getLines;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day$1Test {

    Day$1 day = new Day$1();

    @Test
    void should_solve_example() {
        List<String> lines = getLines(\"day$1/example.txt\");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(114);
    }

    @Test
    void should_solve_part_1() {
        List<String> lines = getLines(\"day$1/input.txt\");

        var result = day.part1(lines);

        assertThat(result).isEqualTo(1980437560);
    }

    @Test
    void should_solve_part_2_example() {
        List<String> lines = getLines(\"day$1/example.txt\");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void should_solve_part_2() {
        List<String> lines = getLines(\"day$1/input.txt\");

        var result = day.part2(lines);

        assertThat(result).isEqualTo(977);
    }
}
" >> "src/test/java/advent_of_code/year$year/day$1/Day$1Test.java"

sed -i '' "s/XX/$1/g" "src/test/java/advent_of_code/year$year/day$1/Day$1Test.java"

# Input files
mkdir -p "src/test/resources/year$year/day$1"
touch "src/test/resources/year$year/day$1/example.txt"
touch "src/test/resources/year$year/day$1/input.txt"
