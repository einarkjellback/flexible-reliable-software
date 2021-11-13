package domain;

import domain.currency.CurrencyStrategy;
import domain.displaying.DisplayStrategy;
import domain.displaying.DurationDisplayStrategy;
import domain.rates.LinearRateStrategy;
import domain.rates.RateStrategy;
import domain.townconfig.AlphaTownFactory;
import domain.townconfig.BetaTownFactory;
import domain.townconfig.PayStationFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegrationTest {

    PayStationFactory factoryStub = new PayStationFactory() {
        @Override
        public RateStrategy createRateStrategy() {
            return new LinearRateStrategy(150);
        }

        @Override
        public Receipt createReceipt(int parkingTime) {
            return null;
        }

        @Override
        public CurrencyStrategy createCurrencyStrategy() {
            return coin -> true;
        }

        @Override
        public DisplayStrategy createDisplayStrategy() {
            return new DurationDisplayStrategy();
        }
    };

    @Nested
    @DisplayName("Integration testing PayStationImpl and LinearRateStrategy")
    class PayStationAndLinearRateStrategy {
        @ParameterizedTest
        @CsvSource({
                "5, 2",
                "10, 4",
                "25, 10"
        })
        public void testSingleCoin(ArgumentsAccessor args) {
            int coin = args.getInteger(0);
            int expected = args.getInteger(1);

            PayStation ps = new PayStationImpl(factoryStub);
            ps.addPayment(coin);

            int actual = ps.readDisplay();
            assertThat(actual, is(expected));
        }

        @Test
        public void testMultipleCoins() {
            List<Integer> coins = List.of(5, 5, 25, 10);
            int expected = 2 + 2 + 10 + 4;

            PayStation ps = new PayStationImpl(factoryStub);
            for (int coin : coins) {
                ps.addPayment(coin);
            }

            int actual = ps.readDisplay();

            assertThat(actual, is(expected));
        }
    }

    @Nested
    @DisplayName("Integration testing PayStationImpl and ProgressiveRateStrategy")
    class PayStationAndProgressiveRateStrategy {
        @Test
        public void testOneDollar() {
            PayStation ps = new PayStationImpl(new BetaTownFactory());
            addOneDollar(ps);

            int expected = 40;
            int actual = ps.readDisplay();
            assertThat(actual, is(expected));
        }

        @Test
        public void testTwoDollars() {
            PayStation ps = new PayStationImpl(new BetaTownFactory());
            addOneDollar(ps);
            addOneDollar(ps);

            int expected = 75;
            int actual = ps.readDisplay();
            assertThat(actual, is(expected));
        }

        @Test
        public void testFiveDollars() {
            PayStation ps = new PayStationImpl(new BetaTownFactory());
            addOneDollar(ps); // 1
            addOneDollar(ps); // 2
            addOneDollar(ps); // 3
            addOneDollar(ps); // 4
            addOneDollar(ps); // 5

            int expected = 150;
            int actual = ps.readDisplay();
            assertThat(actual, is(expected));
        }
    }

    private void addOneDollar(PayStation ps) {
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
    }

    @Nested
    @DisplayName("Integration testing PayStationImpl and CurrencyStrategies")
    class PayStationAndCurrencyStrategy {
        @Test
        public void testUsdStrategy() {
            PayStation ps = new PayStationImpl(new AlphaTownFactory());

            assertThrows(IllegalArgumentException.class, () -> ps.addPayment(1));
        }
    }
}
