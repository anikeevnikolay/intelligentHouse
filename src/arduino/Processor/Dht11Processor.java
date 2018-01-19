package arduino.Processor;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by antiz_000 on 3/18/2016.
 */
public class Dht11Processor extends AbstractProcessor {

    @Override
    public void process(BigDecimal id, String data) throws SQLException, ClassNotFoundException {
        String[] prms = data.split(",");
        saveData(id, "T = " + prms[0] + "; H = " + prms[1]);
    }
}
