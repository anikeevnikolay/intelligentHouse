package helpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/26/2016.
 */
public class SettingsHelper {

    public static Map<String, String> getAvailableSettings(String prName) {
        Properties pr = PropertiesHelper.getProperty(prName);
        return (Map) pr;
    }

    public static void saveParams(Map<String, String> map, String prName) throws IOException {
        Properties pr = new Properties();
        pr.putAll(map);
        pr.store(new FileOutputStream(prName), "last edit");
    }
}
