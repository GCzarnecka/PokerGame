import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ClientTest extends TestCase {

    public void testStart() throws IOException {
        var client = new Client();
        assertFalse(client.start());
    }
}