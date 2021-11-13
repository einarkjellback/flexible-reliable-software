package domain.townconfig;

import domain.Receipt;
import domain.StandardReceipt;
import domain.currency.CurrencyStrategy;
import domain.currency.UsdStrategy;
import domain.displaying.DisplayStrategy;
import domain.displaying.DurationDisplayStrategy;
import domain.rates.ProgressiveRateStrategy;
import domain.rates.RateStrategy;

import java.util.List;

public class BetaTownFactory implements PayStationFactory {
    @Override
    public RateStrategy createRateStrategy() {
        return new ProgressiveRateStrategy(List.of(150, 200, 300));
    }

    @Override
    public Receipt createReceipt(int parkingTime) {
        return new StandardReceipt(parkingTime, true);
    }

    @Override
    public CurrencyStrategy createCurrencyStrategy() {
        return new UsdStrategy();
    }

    @Override
    public DisplayStrategy createDisplayStrategy() {
        return new DurationDisplayStrategy();
    }
}
