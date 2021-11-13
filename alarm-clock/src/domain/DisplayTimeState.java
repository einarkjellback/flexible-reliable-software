package domain;

public class DisplayTimeState extends State {
    public DisplayTimeState(AlarmClockImpl clock) {
        super(clock);
    }

    @Override
    public String readDisplay() {
        return "11:32";
    }

    @Override
    public void mode() {
        clock.setState(clock.setAlarmState);
    }

    @Override
    public void increase() {

    }

    @Override
    public void decrease() {

    }
}
