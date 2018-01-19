package arduino;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by antiz_000 on 3/17/2016.
 */

public class ArduinoRequestAdapter {

    private OutputStream out;

    public ArduinoRequestAdapter(OutputStream output) {
        out = output;
    }

    public void sendRequest(String str) throws InterruptedException, PortInUseException, UnsupportedCommOperationException, IOException {
        try {
            out.write(str.getBytes());
        } catch (IOException e) {
            ArduinoInitialiser.getInstance().init();
        }
    }
}
