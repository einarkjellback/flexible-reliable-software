package domain;

import java.util.ArrayList;
import java.util.List;

public class LedSpy implements SevenSegment {
    public final List<Boolean> signals = new ArrayList<>();

    @Override
    public void setLed(int led, boolean on) {
        signals.add(on);
    }
}
