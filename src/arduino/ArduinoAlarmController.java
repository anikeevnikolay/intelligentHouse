package arduino;

/**
 * Created by antiz_000 on 3/20/2016.
 */
public class ArduinoAlarmController {

    private static String alarmMessage;

    public static void setMessage(String message) {
        alarmMessage = message;
    }

    public static String getAlarmMessage() {
        return alarmMessage;
    }
}
