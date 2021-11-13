package domain;

public interface SevenSegment {

    /**
     * LEDs are numbered from top to bottom, left to right. So top-most LED is 0, top-most left-most 1, top-most
     * right-most 2, and so on.
     * @param led
     * @param on
     */
    void setLed(int led, boolean on);
}
