package arduino.Processor;

import databaseTools.JDBCUtil;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by antiz_000 on 3/17/2016.
 */
public abstract class AbstractProcessor {

    public abstract void process(BigDecimal id, String data) throws SQLException, ClassNotFoundException;

    protected void saveData(BigDecimal id, String data) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertParam(), id, data);
    }
}
