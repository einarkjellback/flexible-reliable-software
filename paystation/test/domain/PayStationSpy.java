package domain;

import domain.townconfig.PayStationFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayStationSpy implements PayStation {
    public final List<Action> actions = new ArrayList<>();

    @Override
    public void addPayment(int coinValue) throws IllegalArgumentException {
        actions.add(Action.ADD_PAYMENT);
    }

    @Override
    public int readDisplay() {
        actions.add(Action.READ_DISPLAY);
        return 0;
    }

    @Override
    public Receipt buy() {
        actions.add(Action.BUY);
        return new Receipt() {
            @Override
            public int value() {
                return 0;
            }

            @Override
            public void print(PrintStream stream) {

            }
        };
    }

    @Override
    public Map<Integer, Integer> cancel() {
        actions.add(Action.CANCEL);
        return null;
    }

    @Override
    public int empty() {
        actions.add(Action.EMPTY);
        return 0;
    }

    @Override
    public void reconfigure(PayStationFactory factory) {
        actions.add(Action.RECONFIGURE);
    }

    public enum Action {
        ADD_PAYMENT, READ_DISPLAY, BUY, CANCEL, EMPTY, RECONFIGURE
    }
}
