package domain.displaying;

import domain.PayStation;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class EndTimeDisplayStrategyTest {
    @Test
    public void testCorrectOutput() {
        final int duration = 142;
        final ZoneId zone = ZoneId.of(ZoneId.getAvailableZoneIds().iterator().next());
        final ZonedDateTime time = ZonedDateTime.of(2021, 10, 11, 8, 21, 0, 0, zone);
        DisplayStrategy displayStrategy = new EndTimeDisplayStrategy(Clock.fixed(
                Instant.from(time),
                zone
        ));

        int actual = displayStrategy.calculateOutput(duration);
        int expected = 1043;

        assertThat(actual, is(expected));
    }
}