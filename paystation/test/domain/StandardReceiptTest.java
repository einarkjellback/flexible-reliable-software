package domain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StandardReceiptTest {
    @Test
    public void testPrintWithoutBarCode() {
        String expected = "-------------------------------------------------\n"
                + "-------   P A R K I N G   R E C E I P T   -------\n"
                + "                Value 049 minutes                \n"
                + "               Car parked at 08:06               \n"
                + "-------------------------------------------------";
        Receipt r = new StandardReceipt(49, false);

        runTest(expected, r);
    }

    @Test
    public void testPrintWithBarCode() {
        String expected = "-------------------------------------------------\n"
                + "-------   P A R K I N G   R E C E I P T   -------\n"
                + "                Value 139 minutes                \n"
                + "               Car parked at 08:06               \n"
                + "| || || |||| | ||| || | | |  |||||| ||| |||| |  |\n"
                + "-------------------------------------------------";
        Receipt r = new StandardReceipt(139, true);

        runTest(expected, r);
    }

    private void runTest(String expected, Receipt r) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        r.print(stream);

        String actual = out.toString();
        assertThat(actual, is(expected));
    }
}
