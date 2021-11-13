package domain.rates;

import domain.rates.LinearRateStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class LinearRateStrategyTest {
    @ParameterizedTest
    @CsvSource({
            "5, 2",
            "10, 4",
            "25, 10"
    })
    public void testSingleCoin(ArgumentsAccessor args) {
        int coin = args.getInteger(0);
        int expected = args.getInteger(1);
        LinearRateStrategy strategy = new LinearRateStrategy(150);

        int actual = strategy.calculateTime(coin);

        assertThat(actual, is(expected));
    }

    @Test
    public void testMultipleCoins() {
        List<Integer> coins = List.of(5, 5, 25, 10);
        int expected = 2 + 2 + 10 + 4;
        LinearRateStrategy strategy = new LinearRateStrategy(150);

        int actual = strategy.calculateTime(coins.stream().reduce(0, Integer::sum));

        assertThat(actual, is(expected));
    }
}