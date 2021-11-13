package domain;

import java.io.PrintStream;

public class StandardReceipt implements Receipt {

    private final int value;
    private final boolean withBarCode;

    public StandardReceipt(int value, boolean withBarCode) {
        this.value = value;
        this.withBarCode = withBarCode;
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public void print(PrintStream stream) {
        String minutes = String.format("%03d", value());
        stream.println("-------------------------------------------------");
        stream.println("-------   P A R K I N G   R E C E I P T   -------");
        stream.println("                Value " + minutes + " minutes                ");
        stream.println("               Car parked at 08:06               ");
        if (withBarCode) {
            stream.println("| || || |||| | ||| || | | |  |||||| ||| |||| |  |");
        }
        stream.print("-------------------------------------------------");
    }
}
