package domain.townconfig;

import domain.Receipt;
import domain.StandardReceipt;
import domain.currency.CurrencyStrategy;
import domain.currency.UsdStrategy;
import domain.displaying.DisplayStrategy;
import domain.displaying.DurationDisplayStrategy;
import domain.displaying.EndTimeDisplayStrategy;
import domain.rates.LinearRateStrategy;
import domain.rates.RateStrategy;

import java.time.Clock;

public class AlphaTownFactory implements PayStationFactory {
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
        return new UsdStrategy();
    }

    @Override
    public DisplayStrategy createDisplayStrategy() {
        return new EndTimeDisplayStrategy(Clock.systemDefaultZone());
    }
}
