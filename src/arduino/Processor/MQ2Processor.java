package arduino.Processor;

import arduino.ArduinoAlarmController;
import helpers.PropertiesHelper;
import helpers.PropertyConsts;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/20/2016.
 */
public class MQ2Processor extends AbstractProcessor {
    @Override
    public void process(BigDecimal id, String data) throws SQLException, ClassNotFoundException {
        try {
            int value = Integer.parseInt(data);
            Properties pr = PropertiesHelper.getProperty(PropertyConsts.ARDUINO_PROPERTIES);
            int specifiedValue = Integer.parseInt(pr.getProperty("critical_fire_value"));
            String text = "";
            if (value > specifiedValue) {
                text = "fire alarm! (";
                ArduinoAlarmController.setMessage("fire alarm! (" + value + ")");
            }
            else {
                text = "normal (";
                ArduinoAlarmController.setMessage(null);
            }
            text += value + " lim=" + specifiedValue + ")";
            saveData(id, text);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Incorrect input message for MQ2 type sensor: \"" + data + "\"!");
        }
    }
}
