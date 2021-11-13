package domain.rates;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class AlternatingRateStrategyTest {
    @Test
    public void testWeekday() {
        WeekendDecisionStrategy fixedDecision = () -> false;
        RateStrategy rateStrategy = new AlternatingRateStrategy(fixedDecision, ((p) -> 1), ((p) -> 2));

        int actual = rateStrategy.calculateTime(0);

        assertThat(actual, is(1));
    }

    @Test
    public void testWeekend() {
        WeekendDecisionStrategy fixedDecision = () -> true;
        RateStrategy rateStrategy = new AlternatingRateStrategy(fixedDecision, ((p) -> 1), ((p) -> 2));

        int actual = rateStrategy.calculateTime(0);

        assertThat(actual, is(2));
    }
}