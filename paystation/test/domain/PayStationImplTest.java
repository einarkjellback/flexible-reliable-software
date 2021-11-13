package domain;

import domain.currency.CurrencyStrategy;
import domain.displaying.DisplayStrategy;
import domain.displaying.DurationDisplayStrategy;
import domain.rates.RateStrategy;
import domain.townconfig.PayStationFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PayStationImplTest {
    private final CurrencyStrategy currencyStrategyMock = coin -> true;
    private final RateStrategy rateStrategyMock = payment -> payment;
    private final PayStationFactory factoryStub = new PayStationFactory() {
        @Override
        public RateStrategy createRateStrategy() {
            return rateStrategyMock;
        }

        @Override
        public Receipt createReceipt(int parkingTime) {
            return new StandardReceipt(parkingTime, false);
        }

        @Override
        public CurrencyStrategy createCurrencyStrategy() {
            return currencyStrategyMock;
        }

        @Override
        public DisplayStrategy createDisplayStrategy() {
            return new DurationDisplayStrategy();
        }
    };

    @Test
    public void testRejectCoin() {
        PayStationFactory factoryStub = new PayStationFactory() {
            @Override
            public RateStrategy createRateStrategy() {
                return null;
            }

            @Override
            public Receipt createReceipt(int parkingTime) {
                return null;
            }

            @Override
            public CurrencyStrategy createCurrencyStrategy() {
                return coin -> false;
            }

            @Override
            public DisplayStrategy createDisplayStrategy() {
                return null;
            }
        };
        PayStation ps = new PayStationImpl(factoryStub);

        assertThrows(IllegalArgumentException.class, () -> ps.addPayment(5));
    }

    @Test
    public void testTimeCalculation() {
        PayStation ps = new PayStationImpl(factoryStub);

        ps.addPayment(10);
        ps.addPayment(5);
        ps.addPayment(25);

        int actual = ps.readDisplay();
        assertThat(actual, is(10 + 5 + 25));
    }

    @Test
    public void invokeBuyShouldResetDisplay() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(5);
        ps.buy();

        assertThat(ps.readDisplay(), is(0));
    }

    @Test
    public void when_cancelCalled_then_displayReset() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(5);
        ps.cancel();

        int actual = ps.readDisplay();

        assertThat(actual, is(0));
    }

    @Test
    public void when_cancel_then_coinsReturned() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(5);
        ps.addPayment(5);
        ps.addPayment(25);

        Map<Integer, Integer> coins = ps.cancel();

        assertThat(coins.get(5), is(2));
        assertNull(coins.get(10));
        assertThat(coins.get(25), is(1));
    }

    @Test
    public void testBuy() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(25);

        Receipt r = ps.buy();

        assertThat(r.value(), is(25));
    }

    @Test
    public void testEmpty() {
        List<Integer> payments = List.of(5, 25, 10, 10);
        int expected = 50;

        PayStation ps = new PayStationImpl(factoryStub);

        ps.addPayment(payments.get(0));
        ps.buy();

        ps.addPayment(payments.get(1));
        ps.addPayment(payments.get(2));
        ps.buy();

        ps.addPayment(payments.get(3));
        ps.buy();

        assertThat(ps.empty(), is(expected));
    }

    @Test
    public void emptyShouldEmptyPayStation() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(25);
        ps.buy();
        ps.empty();

        assertThat(ps.empty(), is(0));
    }

    @Test
    public void given_activePayment_when_emptyCalled_then_exceptionThrown() {
        PayStation ps = new PayStationImpl(factoryStub);
        ps.addPayment(10);

        assertThrows(IllegalStateException.class, ps::empty);
    }

    @Nested
    @DisplayName("Unit testing PayStationImpl.reconfigure(...) and PayStationFactory")
    class PayStationReconfigureAndFactory {
        private final PayStationFactory factoryStubInit = new PayStationFactory() {
            @Override
            public RateStrategy createRateStrategy() {
                return null;
            }

            @Override
            public Receipt createReceipt(int parkingTime) {
                return null;
            }

            @Override
            public CurrencyStrategy createCurrencyStrategy() {
                return null;
            }

            @Override
            public DisplayStrategy createDisplayStrategy() {
                return null;
            }
        };
        private final PayStation ps = new PayStationImpl(factoryStubInit);

        @Test
        public void testReassignCurrencyStrategy() {
            PayStationFactory factoryStub = new PayStationFactory() {
                @Override
                public RateStrategy createRateStrategy() {
                    return null;
                }

                @Override
                public Receipt createReceipt(int parkingTime) {
                    return null;
                }

                @Override
                public CurrencyStrategy createCurrencyStrategy() {
                    return coin -> false;
                }

                @Override
                public DisplayStrategy createDisplayStrategy() {
                    return null;
                }
            };

            ps.reconfigure(factoryStub);

            assertThrows(IllegalArgumentException.class, () -> ps.addPayment(1));
        }

        @Test
        public void testRateStrategy() {
            PayStationFactory factoryStub = new PayStationFactory() {
                @Override
                public RateStrategy createRateStrategy() {
                    return payment -> 1;
                }

                @Override
                public Receipt createReceipt(int parkingTime) {
                    return null;
                }

                @Override
                public CurrencyStrategy createCurrencyStrategy() {
                    return null;
                }

                @Override
                public DisplayStrategy createDisplayStrategy() {
                    return minutes -> minutes;
                }
            };

            ps.reconfigure(factoryStub);

            assertThat(ps.readDisplay(), is(1));
        }

        @Test
        public void testReceipt() {
            PayStationFactory factoryStub = new PayStationFactory() {
                @Override
                public RateStrategy createRateStrategy() {
                    return payment -> payment;
                }

                @Override
                public Receipt createReceipt(int parkingTime) {
                    return new Receipt() {
                        @Override
                        public int value() {
                            return -1;
                        }

                        @Override
                        public void print(PrintStream stream) {

                        }
                    };
                }

                @Override
                public CurrencyStrategy createCurrencyStrategy() {
                    return null;
                }

                @Override
                public DisplayStrategy createDisplayStrategy() {
                    return null;
                }
            };

            ps.reconfigure(factoryStub);

            assertThat(ps.buy().value(), is(-1));
        }
    }
}