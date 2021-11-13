package domain.observing;

import domain.PayStation;
import domain.PayStationSpy;
import domain.Receipt;
import domain.observing.ObserverSpy;
import domain.observing.PayStationSubjectDecorator;
import domain.townconfig.PayStationFactory;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static domain.PayStationSpy.Action.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PayStationSubjectDecoratorTest {

    @Test
    public void testIsDecorator() {
        PayStationSpy spy = new PayStationSpy();
        PayStation decorator = new PayStationSubjectDecorator(spy);

        decorator.reconfigure(null);
        decorator.addPayment(0);
        decorator.buy();
        decorator.readDisplay();
        decorator.empty();
        decorator.cancel();

        final List<Object> expected = List.of(
                RECONFIGURE,
                ADD_PAYMENT,
                BUY,
                READ_DISPLAY,
                EMPTY,
                CANCEL
        );
        assertThat(spy.actions, is(expected));
    }

    @Test
    public void testNotifyObserversOnBuy() {
        final int expected = 13;
        class PayStationStubCustomBuy implements PayStation {
            @Override
            public Receipt buy() {
                return new Receipt() {
                    @Override
                    public int value() {
                        return expected;
                    }

                    @Override
                    public void print(PrintStream stream) {

                    }
                };
            }

            @Override
            public void addPayment(int coinValue) throws IllegalArgumentException {

            }

            @Override
            public int readDisplay() {
                return 0;
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

        ObserverSpy spy = new ObserverSpy();
        PayStationSubjectDecorator ps = new PayStationSubjectDecorator(new PayStationStubCustomBuy());
        ps.addObserver(spy);

        ps.buy();

        assertThat(spy.calls.get(0), is(expected));
    }
}