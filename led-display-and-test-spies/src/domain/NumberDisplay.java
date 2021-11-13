package domain;

import java.util.List;
import java.util.Map;

public class NumberDisplay {

    private final SevenSegment leds;

    private static final Map<Number, List<Boolean>> signals = Map.of(
            Number.ZERO, List.of(true, true, true, false, true, true, true),
            Number.ONE, List.of(false, true, false, false, true, false, false),
            Number.TWO, List.of(true, false, true, true, true, false, true),
            Number.THREE, List.of(true, false, true, true, false, true, true),
            Number.FOUR, List.of(false, true, true, true, false, true, false),
            Number.FIVE, List.of(true, true, false, true, false, true, true),
            Number.SIX, List.of(true, true, false, true, true, true, true),
            Number.SEVEN, List.of(true, false, true, false, false, true, false),
            Number.EIGHT, List.of(true, true, true, true, true, true, true),
            Number.NINE, List.of(true, true, true, true, false, true, false)
    );

    public NumberDisplay(SevenSegment leds) {
        this.leds = leds;
    }

    public void display(Number n) {
        final int SEGMENTS = 7;
        final List<Boolean> signals = NumberDisplay.signals.get(n);
        for (int i = 0; i < SEGMENTS; i++) {
            leds.setLed(i, signals.get(i));
        }
    }
}
