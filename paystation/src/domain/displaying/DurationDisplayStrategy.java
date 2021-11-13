package domain.displaying;

public class DurationDisplayStrategy implements DisplayStrategy {
    @Override
    public int calculateOutput(int minutes) {
        return minutes;
    }
}
