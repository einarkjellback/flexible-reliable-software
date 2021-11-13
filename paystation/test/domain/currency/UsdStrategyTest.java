package domain.currency;

import domain.currency.CurrencyStrategy;
import domain.currency.UsdStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsdStrategyTest {
    @ParameterizedTest
    @ValueSource(ints = {5, 10, 25})
    public void testIsValid_validCoin(int coin) {
        CurrencyStrategy strategy = new UsdStrategy();

        boolean actual = strategy.isValid(coin);

        assertTrue(actual);
    }

    @Test
    public void testIsValid_invalidCoin() {
        CurrencyStrategy strategy = new UsdStrategy();

        boolean actual = strategy.isValid(1);

        assertFalse(actual);
    }
}