package arduino;

import databaseTools.ArduinoTool;

import java.io.InputStream;

/**
 * Created by antiz_000 on 3/17/2016.
 */
public class ArduinoListener implements Runnable {

    private InputStream input;

    public ArduinoListener(InputStream in) {
        input = in;
    }

    @Override
    public void run() {
        while (true)
            try {
                if (input.available() > 0) {
                    String str = "";
                    while (input.available() > 0) {
                        str += (char) input.read();
                    }
                    ArduinoTool.processMessage(str);
                }

                Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    ArduinoInitialiser.getInstance().init();
                    Thread.sleep(2000);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }
}
