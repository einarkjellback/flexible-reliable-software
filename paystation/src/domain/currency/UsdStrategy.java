package domain.currency;

public class UsdStrategy implements CurrencyStrategy {
    @Override
    public boolean isValid(int coin) {
        return coin == 5 || coin == 10 || coin == 25;
    }
}
