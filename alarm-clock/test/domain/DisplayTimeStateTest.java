package domain;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class DisplayTimeStateTest {
    private final AlarmClock clock = new DisplayTimeState(new AlarmClockImpl());

    @Test
    public void given_freshAlarmClock_when_readDisplay_then_1137() {
        checkTime("11:32");
    }

    @Test
    public void given_freshClock_when_increase_then_nothingHappens() {
        clock.increase();
        checkTime("11:32");
    }

    @Test
    public void given_freshClock_when_decrease_then_nothingHappens() {
        clock.increase();
        checkTime("11:32");
    }

    private void checkTime(String s) {
        MatcherAssert.assertThat(clock.readDisplay(), is(s));
    }
}
