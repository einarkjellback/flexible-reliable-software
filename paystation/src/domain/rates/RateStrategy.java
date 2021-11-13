package domain.rates;

@FunctionalInterface
public interface RateStrategy {
    /**
     * Calculates the amount of parking time that is available given a payment.
     * @param payment the total sum of money used to buy parking time.
     * @return the resulting parking time.
     */
    int calculateTime(int payment);
}
