package advent_of_code.year2022.day4;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SegmentTest {

    public static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of(new Segment(2, 4), new Segment(6, 8), false),
            Arguments.of(new Segment(2, 4), new Segment(3, 8), true),
            Arguments.of(new Segment(2, 4), new Segment(1, 3), true)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void should_return_true_if_overlapping(Segment first, Segment second, boolean expected) {
        assertThat(first.overlaps(second)).isEqualTo(expected);
    }
}
// 2-4,6-8
//2-3,4-5
//5-7,7-9
//2-8,3-7
//6-6,4-6
//2-6,4-8
