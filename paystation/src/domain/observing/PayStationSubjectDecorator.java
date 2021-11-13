package domain.observing;

import domain.PayStation;
import domain.Receipt;
import domain.townconfig.PayStationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayStationSubjectDecorator implements PayStation, Subject {
    private final PayStation payStation;
    private final List<Observer> observers = new ArrayList<>();

    public PayStationSubjectDecorator(PayStation ps) {
        this.payStation = ps;
    }

    @Override
    public void addPayment(int coinValue) throws IllegalArgumentException {
        payStation.addPayment(coinValue);
    }

    @Override
    public int readDisplay() {
        return payStation.readDisplay();
    }

    @Override
    public Receipt buy() {
        final Receipt receipt = payStation.buy();
        notify(receipt.value());
        return receipt;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        return payStation.cancel();
    }

    @Override
    public int empty() {
        return payStation.empty();
    }

    @Override
    public void reconfigure(PayStationFactory factory) {
        payStation.reconfigure(factory);
    }

    @Override
    public void notify(int timeBought) {
        for (Observer o : observers) {
            o.update(timeBought);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }
}
