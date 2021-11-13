package domain.displaying;

import java.time.Clock;
import java.time.LocalTime;

public class EndTimeDisplayStrategy implements DisplayStrategy {
    private final Clock clock;

    public EndTimeDisplayStrategy(Clock clock) {
        this.clock = clock;
    }

    @Override
    public int calculateOutput(int minutes) {
        LocalTime endTime = LocalTime.now(clock).plusMinutes(minutes);
        return 100 * endTime.getHour() + endTime.getMinute();
    }
}
