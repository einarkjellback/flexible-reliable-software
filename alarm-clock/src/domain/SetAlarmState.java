package domain;

import java.time.LocalTime;

public class SetAlarmState extends State {
    private LocalTime time = LocalTime.of(6, 0);
    private TimeMode timeMode = TimeMode.SET_HOURS;

    public SetAlarmState(AlarmClockImpl clock) {
        super(clock);
    }

    @Override
    public String readDisplay() {
        return this.time.toString();
    }

    @Override
    public void mode() {
        if (timeMode == TimeMode.SET_HOURS) {
            timeMode = TimeMode.SET_MINUTES;
        } else {
            timeMode = TimeMode.SET_HOURS;
            clock.setState(clock.displayTimeState);
        }
    }

    @Override
    public void increase() {
        if (timeMode == TimeMode.SET_HOURS) {
            time = time.plusHours(1);
        } else {
            time = time.plusMinutes(1);
            if (time.getMinute() == 0) {
                time = time.minusHours(1); // We want hours to remain the same as before incrementing minutes
            }
        }
    }

    @Override
    public void decrease() {
        if (timeMode == TimeMode.SET_HOURS) {
            time = time.minusHours(1);
        } else {
            time = time.minusMinutes(1);
            if (time.getMinute() == 59) {
                time = time.plusHours(1); // We want hours to remain the same as before decrementing minutes
            }
        }
    }

    private enum TimeMode {
        SET_HOURS, SET_MINUTES
    }
}
