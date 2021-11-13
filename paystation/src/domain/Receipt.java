package domain;

import java.io.PrintStream;

public interface Receipt {
    int value();

    void print(PrintStream stream);
}
