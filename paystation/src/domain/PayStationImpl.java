package domain;

import domain.currency.CurrencyStrategy;
import domain.displaying.DisplayStrategy;
import domain.rates.RateStrategy;
import domain.townconfig.PayStationFactory;

import java.util.HashMap;
import java.util.Map;

public class PayStationImpl implements PayStation {
    private PayStationFactory factory;

    private CurrencyStrategy currencyStrategy;
    private DisplayStrategy displayStrategy;
    private RateStrategy rateStrategy;

    private int totalRevenue;
    private final Map<Integer, Integer> coins = new HashMap<>();

    public PayStationImpl(PayStationFactory factory) {
        this.factory = factory;
        this.rateStrategy = factory.createRateStrategy();
        this.currencyStrategy = factory.createCurrencyStrategy();
        this.displayStrategy =  factory.createDisplayStrategy();
    }

    @Override
    public void addPayment(int coinValue) throws IllegalArgumentException {
        if (!currencyStrategy.isValid(coinValue)) {
            throw new IllegalArgumentException(coinValue + " is an invalid coin");
        }

        int coinCount = coins.getOrDefault(coinValue, 0);
        coins.put(coinValue, ++coinCount);
    }

    @Override
    public int readDisplay() {
        final int duration = rateStrategy.calculateTime(getCoinSum());
        return displayStrategy.calculateOutput(duration);
    }

    @Override
    public Receipt buy() {
        final int value = rateStrategy.calculateTime(getCoinSum());
        final Receipt getTime = factory.createReceipt(value);
        totalRevenue += getCoinSum();
        coins.replaceAll((k, v) -> 0);
        return getTime;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> temp = new HashMap<>(coins);
        coins.replaceAll((k, v) -> 0);
        return temp;
    }

    public int empty() {
        if (isHandlingCustomer()) {
            throw new IllegalStateException("Currently handling a payment");
        }

        int temp = totalRevenue;
        totalRevenue = 0;
        return temp;
    }

    @Override
    public void reconfigure(PayStationFactory factory) {
        currencyStrategy = factory.createCurrencyStrategy();
        rateStrategy = factory.createRateStrategy();
        displayStrategy = factory.createDisplayStrategy();
        this.factory = factory;
    }

    private boolean isHandlingCustomer() {
        return getCoinSum() > 0;
    }

    private int getCoinSum() {
        return coins.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .reduce(0, Integer::sum);
    }
}
