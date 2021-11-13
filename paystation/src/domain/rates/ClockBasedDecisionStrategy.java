package domain.rates;

import java.time.DayOfWeek;
import java.time.Instant;

public class ClockBasedDecisionStrategy implements WeekendDecisionStrategy {
    @Override
    public boolean isWeekend() {
        final DayOfWeek today = DayOfWeek.from(Instant.now());
        return today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY;
    }
}
