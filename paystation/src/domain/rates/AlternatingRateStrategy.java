package domain.rates;

public class AlternatingRateStrategy implements RateStrategy {
    private final RateStrategy weekdayRate;
    private final RateStrategy weekendRate;
    private final WeekendDecisionStrategy decisionStrategy;

    public AlternatingRateStrategy(WeekendDecisionStrategy decisionStrategy, RateStrategy weekdayRate,
                                   RateStrategy weekendRate) {
        this.weekdayRate = weekdayRate;
        this.weekendRate = weekendRate;
        this.decisionStrategy = decisionStrategy;
    }

    @Override
    public int calculateTime(int payment) {
        if (decisionStrategy.isWeekend()) {
            return weekendRate.calculateTime(payment);
        }
        return weekdayRate.calculateTime(payment);
    }
}
