package databaseTools;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by antiz_000 on 2/25/2016.
 */
public class ErrorTool {

    public static Object[] getErrorRecord(BigDecimal id) throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectError(), id).get(0);
    }

    public static List<Object[]> getJobsErrors(BigDecimal jobId) throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectJobErrors(), jobId);
    }
}
