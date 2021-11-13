package domain;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class SetAlarmStateTest {
    private final AlarmClock clock = new SetAlarmState(new AlarmClockImpl());

    @Test
    public void freshClockShouldDisplay0600() {
        checkTime("06:00");
    }

    @Nested
    class TestIncrease {

        @Test
        public void increasingMinutesBy60ShouldNotIncreaseHour() {
            clock.mode();
            inc32();
            inc32();
            checkTime("06:04");
        }

        @Test
        public void setAlarmTo0705() {
            clock.increase();
            clock.mode();
            inc4();
            clock.increase();
            checkTime("07:05");
        }

        @Test
        public void setAlarmTo0401() {
            inc16();
            inc4();
            inc2();
            clock.mode();
            clock.increase();
            checkTime("04:01");
        }

        private void inc2() {
            clock.increase();
            clock.increase();
        }

        private void inc4() {
            inc2(); inc2();
        }

        private void inc8() {
            inc4(); inc4();
        }

        private void inc16() {
            inc8(); inc8();
        }

        private void inc32() {
            inc16();
            inc16();
        }


    }

    @Nested
    class TestDecrease {
        @Test
        public void setAfterMidnight() {
            dec4();
            clock.mode();
            dec4();
            dec2();
            clock.decrease();
            checkTime("02:53");
        }

        @Test
        public void setBeforeMidnight() {
            dec8();
            clock.mode();
            dec8();
            dec2();
            checkTime("22:50");
        }

        private void dec8() {
            dec4();
            dec4();
        }

        private void dec4() {
            dec2();
            dec2();
        }

        private void dec2() {
            clock.decrease();
            clock.decrease();
        }
    }

    private void checkTime(String s) {
        MatcherAssert.assertThat(clock.readDisplay(), is(s));
    }

}
