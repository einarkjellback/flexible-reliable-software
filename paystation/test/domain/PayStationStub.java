package domain;

import domain.townconfig.PayStationFactory;

import java.util.Map;

public class PayStationStub implements PayStation {
    @Override
    public void addPayment(int coinValue) throws IllegalArgumentException {

    }

    @Override
    public int readDisplay() {
        return 0;
    }

    @Override
    public Receipt buy() {
        return null;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        return null;
    }

    @Override
    public int empty() {
        return 0;
    }

    @Override
    public void reconfigure(PayStationFactory factory) {

    }
}
