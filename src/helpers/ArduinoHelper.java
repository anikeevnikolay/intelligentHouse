package helpers;

import arduino.ArduinoInitialiser;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/19/2016.
 */
public class ArduinoHelper {

    public void sendMessageToArduino(String message) throws InterruptedException, PortInUseException, UnsupportedCommOperationException, IOException {
        ArduinoInitialiser.getInstance().getArduinoRequestAdapter().sendRequest(message);
    }

    public void syncAlarmLimit() throws InterruptedException, PortInUseException, UnsupportedCommOperationException, IOException {
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.ARDUINO_PROPERTIES);
        String message = "set_alarm_limit " + pr.getProperty("critical_fire_value");
        ArduinoInitialiser.getInstance().getArduinoRequestAdapter().sendRequest(message);
    }
}
