package domain.currency;

import domain.currency.CurrencyStrategy;
import domain.currency.DkkStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DkkStrategyTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 20})
    public void testIsValid_validCoin(int coin) {
        CurrencyStrategy strategy = new DkkStrategy();

        boolean actual = strategy.isValid(coin);

        assertTrue(actual);
    }

    @Test
    public void testIsValid_invalidCoin() {
        CurrencyStrategy strategy = new DkkStrategy();

        boolean actual = strategy.isValid(25);

        assertFalse(actual);
    }
}