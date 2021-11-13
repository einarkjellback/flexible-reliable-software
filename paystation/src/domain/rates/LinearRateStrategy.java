package domain.rates;

public class LinearRateStrategy implements RateStrategy {
    private final double hourlyRate;

    /**
     * Calculates parking times according to a fixed hourly rate expressed in whatever currency applies.
     * @param hourlyRate the cost of one hour of parking.
     */
    public LinearRateStrategy(double hourlyRate) {
        this.hourlyRate  = hourlyRate;
    }

    @Override
    public int calculateTime(int payment) {
        return (int) (payment * 60.0 / hourlyRate);
    }
}
