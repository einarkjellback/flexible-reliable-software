package domain.currency;

public class DkkStrategy implements CurrencyStrategy {
    @Override
    public boolean isValid(int coin) {
        return coin == 1 || coin == 2 || coin == 5 || coin == 10 || coin == 20;
    }
}
