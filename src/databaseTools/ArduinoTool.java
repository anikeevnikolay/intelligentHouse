package databaseTools;

import arduino.Processor.AbstractProcessor;
import compile.ClassLoadHelper;
import helpers.JavaHelper;
import helpers.PropertiesHelper;
import helpers.PropertyConsts;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/17/2016.
 */
public class ArduinoTool {

    public static Class<? extends AbstractProcessor> getSensorProcessor(BigDecimal deviceId) throws SQLException, ClassNotFoundException, IOException {
        String className = (String) JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().selectProcessorClassByDeviceId(), deviceId);
        if (className == null)
            throw new UnsupportedOperationException("Processor for " + deviceId + " sensor not specified");
        Class result = ClassLoadHelper.loadClassByName(className);
        if (!isExtendsAbstractProcessor(result))
            throw new ClassCastException(result.getCanonicalName() + " don't extend AbstractProcessor");
        return result;
    }

    public static void processMessage(String message) throws SQLException, ClassNotFoundException {
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.ARDUINO_PROPERTIES);
        boolean keepHistory = Boolean.valueOf(pr.getProperty("keep_history"));
        if (keepHistory)
            JavaHelper.saveErrorRecord(DBConsts.ARDUINO_HISTORY_JOB_ID, "history record", message);
        if (!message.startsWith("begin") || !message.endsWith("end")) {
            JavaHelper.saveErrorRecord(DBConsts.ARDUINO_REQUEST_PROCESSOR_JOB_ID, "incorrect message", "incorrect message from Arduino:\n" + message);
            return;
        }
        String clearMessage = message.substring(6, message.length() - 4);
        String[] inputArr = clearMessage.split("\n");
        for (String input : inputArr) {
            try {
                processInputParam(input);
            } catch (Exception e) {
                String errorText = "";
                for (StackTraceElement s : e.getStackTrace())
                    errorText += s.toString() + "\n";
                JavaHelper.saveErrorRecord(DBConsts.ARDUINO_REQUEST_PROCESSOR_JOB_ID, e.getClass().getSimpleName() + " (" + e.getMessage() + ")", errorText);
            }
        }
    }

    public static void processInputParam(String data) throws SQLException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String[] arr = data.split(";");
        BigDecimal sensorId = new BigDecimal(arr[0]);
        String value = arr[1];
        Class<? extends AbstractProcessor> cl = getSensorProcessor(sensorId);
        if (cl != null)
            cl.newInstance().process(sensorId, value);
    }

    private static boolean isExtendsAbstractProcessor(Class cl) {
        if (cl.equals(Object.class))
            return false;
        if (cl.equals(AbstractProcessor.class))
            return true;
        return isExtendsAbstractProcessor(cl.getSuperclass());
    }
}
