package helpers;

import java.io.*;
import java.util.Properties;

/**
 * Created by antiz_000 on 3/20/2016.
 */
public class PropertiesHelper {
    public static Properties getProperty(String name) throws IllegalStateException {
        Properties pr = new Properties();
        File f = new File(name);
        try (InputStream is = new FileInputStream(f)) {
            pr.load(is);
            return pr;
        } catch (IOException e) {
            throw new IllegalStateException("Property \"" + name + "\" not found");
        }
    }
}
