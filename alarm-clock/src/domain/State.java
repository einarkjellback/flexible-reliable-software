package domain;

public abstract class State implements AlarmClock {
    protected final AlarmClockImpl clock;

    protected State(AlarmClockImpl clock) {
        this.clock = clock;
    }
}
