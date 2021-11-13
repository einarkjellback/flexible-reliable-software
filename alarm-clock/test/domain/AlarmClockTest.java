package domain;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AlarmClockTest {
    private final AlarmClockImpl clock = new AlarmClockImpl();

    @Test
    public void when_modePressed_then_0600() {
        clock.mode();
        checkTime("06:00");
    }

    @Test
    public void when_modePressed3Times_then_1132() {
        clock.mode();
        clock.mode();
        clock.mode();
        checkTime("11:32");
    }

    @Test
    public void freshObjectDisplays1132() {
        checkTime("11:32");
    }

    private void checkTime(String s) {
        MatcherAssert.assertThat(clock.readDisplay(), is(s));
    }
}