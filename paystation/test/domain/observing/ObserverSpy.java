package domain.observing;

import domain.observing.Observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverSpy implements Observer {
    final List<Integer> calls = new ArrayList<>();

    @Override
    public void update(int timeBought) {
        calls.add(timeBought);
    }
}
