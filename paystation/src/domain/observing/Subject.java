package domain.observing;

public interface Subject {
    void notify(int timeBought);

    void addObserver(Observer o);
}
