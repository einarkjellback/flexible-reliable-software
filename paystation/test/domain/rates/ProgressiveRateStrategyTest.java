package domain.rates;

import domain.rates.ProgressiveRateStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ProgressiveRateStrategyTest {
    @ParameterizedTest
    @CsvSource({
            "150, 60",
            "350, 120",
            "650, 180",
            "950, 240"
    })
    public void testCalculate(ArgumentsAccessor args) {
        int payment = args.getInteger(0); // cents
        final ProgressiveRateStrategy strategy = new ProgressiveRateStrategy(List.of(150, 200, 300));
        final int actual = strategy.calculateTime(payment);
        final int expected = args.getInteger(1); /* minutes */
        assertThat(actual, is(expected));
    }
}