package domain;

public class AlarmClockImpl implements AlarmClock {

    public final State setAlarmState = new SetAlarmState(this);
    public final State displayTimeState = new DisplayTimeState(this);
    private State currentState = displayTimeState;

    @Override
    public String readDisplay() {
        return currentState.readDisplay();
    }

    @Override
    public void mode() {
        currentState.mode();
    }

    @Override
    public void increase() {
        currentState.increase();
    }

    @Override
    public void decrease() {
        currentState.decrease();
    }

    void setState(State s) {
        this.currentState = s;
    }
}
