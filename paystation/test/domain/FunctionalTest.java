package domain;

import domain.currency.CurrencyStrategy;
import domain.currency.DkkStrategy;
import domain.displaying.DisplayStrategy;
import domain.displaying.DurationDisplayStrategy;
import domain.rates.LinearRateStrategy;
import domain.rates.ProgressiveRateStrategy;
import domain.rates.RateStrategy;
import domain.townconfig.AlphaTownFactory;
import domain.townconfig.BetaTownFactory;
import domain.townconfig.PayStationFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FunctionalTest {
    @Test
    public void UsaLinearRate() {
        PayStationFactory factoryStub = new PayStationFactory() {
            @Override
            public RateStrategy createRateStrategy() {
                return new LinearRateStrategy(150);
            }

            @Override
            public Receipt createReceipt(int parkingTime) {
                return new StandardReceipt(parkingTime, false);
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
        PayStation ps = new PayStationImpl(factoryStub);
        List<Integer> displayedTimes = new ArrayList<>();

        ps.addPayment(25);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(5);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(10);
        displayedTimes.add(ps.readDisplay());

        final List<Integer> expected = List.of(10, 12, 16);

        assertThat(displayedTimes, is(expected));
        assertThat(ps.buy().value(), is(expected.get(2)));
        assertThat(ps.readDisplay(), is(0));
    }

    @Test
    public void DkkLinearRate() {
        PayStationFactory factoryStub = new PayStationFactory() {
            @Override
            public RateStrategy createRateStrategy() {
                return new LinearRateStrategy(60.0 / 7.0);
            }

            @Override
            public Receipt createReceipt(int parkingTime) {
                return new StandardReceipt(parkingTime, false);
            }

            @Override
            public CurrencyStrategy createCurrencyStrategy() {
                return new DkkStrategy();
            }

            @Override
            public DisplayStrategy createDisplayStrategy() {
                return new DurationDisplayStrategy();
            }
        };
        PayStation ps = new PayStationImpl(factoryStub);
        List<Integer> displayedTimes = new ArrayList<>();

        ps.addPayment(1);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(2);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(5);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(10);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(20);
        displayedTimes.add(ps.readDisplay());

        final List<Integer> expected = List.of(
                7,
                7*(2 + 1),
                7*(5 + 2 + 1),
                7*(10 + 5 + 2 + 1),
                7*(20 + 10 + 5 + 2 + 1)
        );

        assertThat(displayedTimes, is(expected));
        assertThat(ps.buy().value(), is(expected.get(4)));
        assertThat(ps.readDisplay(), is(0));
    }

    @Test
    public void UsdProgressiveRate() {
        PayStation ps = new PayStationImpl(new BetaTownFactory());
        List<Integer> displayedTimes = new ArrayList<>();

        ps.addPayment(25);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(5); // Total == 30
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(10); // Total == 40
        displayedTimes.add(ps.readDisplay());

        addOneUsdDollar(ps);
        addOneUsdDollar(ps); // Total == 240
        displayedTimes.add(ps.readDisplay());

        addOneUsdDollar(ps);
        addOneUsdDollar(ps); // Total == 440
        displayedTimes.add(ps.readDisplay());

        final List<Integer> expected = List.of(10, 12, 16, 87, 138);

        assertThat(displayedTimes, is(expected));
        assertThat(ps.buy().value(), is(expected.get(4)));
        assertThat(ps.readDisplay(), is(0));
    }

    private void addOneUsdDollar(PayStation ps) {
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
    }

    @Test
    public void DkkProgressiveRate() {
        PayStationFactory factoryStub = new PayStationFactory() {
            @Override
            public RateStrategy createRateStrategy() {
                return new ProgressiveRateStrategy(List.of(10, 10, 12));
            }

            @Override
            public Receipt createReceipt(int parkingTime) {
                return new StandardReceipt(parkingTime, false);
            }

            @Override
            public CurrencyStrategy createCurrencyStrategy() {
                return new DkkStrategy();
            }

            @Override
            public DisplayStrategy createDisplayStrategy() {
                return new DurationDisplayStrategy();
            }
        };
        PayStation ps = new PayStationImpl(factoryStub);
        List<Integer> displayedTimes = new ArrayList<>();

        ps.addPayment(1);
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(2); // Total = 3
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(5); // Total = 8
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(10); // Total = 18
        displayedTimes.add(ps.readDisplay());

        ps.addPayment(20); // Total = 38
        displayedTimes.add(ps.readDisplay());

        final List<Integer> expected = List.of(
                6,
                6*(2 + 1),
                6*(5 + 2 + 1),
                6*(10 + 5 + 2 + 1),
                (6*20) + (5*18)
        );

        assertThat(displayedTimes, is(expected));
        assertThat(ps.buy().value(), is(expected.get(4)));
        assertThat(ps.readDisplay(), is(0));
    }
}
