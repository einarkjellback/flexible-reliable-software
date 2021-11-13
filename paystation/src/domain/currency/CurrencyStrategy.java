package domain.currency;

@FunctionalInterface
public interface CurrencyStrategy {
    /**
     * Determines whether the coin is valid.
     * @param coin to check.
     * @return true if coin is valid, else false.
     */
    boolean isValid(int coin);
}
