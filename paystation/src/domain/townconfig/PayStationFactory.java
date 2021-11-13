package domain.townconfig;

import domain.Receipt;
import domain.currency.CurrencyStrategy;
import domain.displaying.DisplayStrategy;
import domain.rates.RateStrategy;

public interface PayStationFactory {
    RateStrategy createRateStrategy();

    Receipt createReceipt(int parkingTime);

    CurrencyStrategy createCurrencyStrategy();

    DisplayStrategy createDisplayStrategy();
}
