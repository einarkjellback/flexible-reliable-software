package domain;

import domain.observing.Observer;

public class PayStationMonitor implements Observer {

    private int totalTimeBought;

    @Override
    public void update(int timeBought) {
        totalTimeBought += timeBought;
    }

    public int totalTimeBought() {
        return totalTimeBought;
    }
}
