package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NumberDisplayTest {
    @ParameterizedTest
    @MethodSource("generateData")
    public void testNumbers(Number n, List<Boolean> expected) {
        LedSpy spy = new LedSpy();
        NumberDisplay display = new NumberDisplay(spy);

        display.display(n);

        assertThat(spy.signals, is(expected));
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(Number.ZERO, List.of(true, true, true, false, true, true, true)),
                Arguments.of(Number.ONE, List.of(false, true, false, false, true, false, false)),
                Arguments.of(Number.TWO, List.of(true, false, true, true, true, false, true)),
                Arguments.of(Number.THREE, List.of(true, false, true, true, false, true, true)),
                Arguments.of(Number.FOUR, List.of(false, true, true, true, false, true, false)),
                Arguments.of(Number.FIVE, List.of(true, true, false, true, false, true, true)),
                Arguments.of(Number.SIX, List.of(true, true, false, true, true, true, true)),
                Arguments.of(Number.SEVEN, List.of(true, false, true, false, false, true, false)),
                Arguments.of(Number.EIGHT, List.of(true, true, true, true, true, true, true)),
                Arguments.of(Number.NINE, List.of(true, true, true, true, false, true, false))
        );
    }

}
