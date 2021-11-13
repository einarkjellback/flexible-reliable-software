package domain;

import domain.observing.PayStationSubjectDecorator;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PayStationMonitorTest {
    @Test
    public void testTotalTimeBought() {
        final int timeBought = 41;
        class PayStationStubCustomBuy extends PayStationStub {
            @Override
            public Receipt buy() {
                return new Receipt() {
                    @Override
                    public int value() {
                        return timeBought;
                    }

                    @Override
                    public void print(PrintStream stream) {

                    }
                };
            }
        }
        PayStationMonitor monitor = new PayStationMonitor();

        PayStationSubjectDecorator payStationA = new PayStationSubjectDecorator(new PayStationStubCustomBuy());
        PayStationSubjectDecorator payStationB = new PayStationSubjectDecorator(new PayStationStubCustomBuy());

        payStationA.addObserver(monitor);
        payStationB.addObserver(monitor);

        payStationA.buy();
        payStationB.buy();

        assertThat(monitor.totalTimeBought(), is(timeBought * 2));
    }
}