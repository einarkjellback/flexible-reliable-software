package domain;

import domain.townconfig.PayStationFactory;

import java.util.Map;

public interface PayStation {
    void addPayment(int coinValue) throws IllegalArgumentException;

    int readDisplay();

    Receipt buy();

    Map<Integer, Integer> cancel();

    int empty();

    void reconfigure(PayStationFactory factory);
}
