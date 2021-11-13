package domain.rates;

import java.util.List;

public class ProgressiveRateStrategy implements RateStrategy {
    private final List<Integer> hourlyRates;

    public ProgressiveRateStrategy(List<Integer> hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    @Override
    public int calculateTime(int payment) {
        final int periods = hourlyRates.size();
        int totalTime = 0;
        for (int HourN = 0; payment > 0; HourN++) {
            final int hourlyRate = hourlyRates.get(HourN);
            final boolean onLastPeriod = HourN + 1 >= periods;
            if (payment <= hourlyRate || onLastPeriod) {
                return totalTime + convertPaymentToTime(payment, hourlyRates.get(HourN));
            } else {
                totalTime += 60;
                payment -= hourlyRate;
            }
        }
        return totalTime;
    }

    private int convertPaymentToTime(int payment, int hourlyRate) {
        return payment * 60 / hourlyRate;
    }
}
