package databaseTools;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by antiz_000 on 2/22/2016.
 */
public class JobTool {

    public static void setJobEnable(BigDecimal jobId) throws SQLException, ClassNotFoundException {
        setJobStatus(jobId, BigDecimal.ONE);
    }

    public static void setJobDisable(BigDecimal jobId) throws SQLException, ClassNotFoundException {
        setJobStatus(jobId, BigDecimal.valueOf(2));
    }

    private static void setJobStatus(BigDecimal jobId, BigDecimal status) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().updateJobStatus(), status, jobId);
    }

    public static BigDecimal createNewJob() throws SQLException, ClassNotFoundException {
        BigDecimal id = JDBCUtil.getNewId();
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertNewJob(), id);
        return id;
    }

    public static void deleteJob(BigDecimal jobId) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().deleteJob(), jobId);
    }

    public static Object[] getJobInfo(BigDecimal id) throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectJob(), id).get(0);
    }

    public static void updateJob(BigDecimal id, String name, String body, String timeout, String status, String type) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().updateJob(),
                name,
                body,
                new BigDecimal(timeout),
                "on".equals(status) ? BigDecimal.ONE : BigDecimal.valueOf(2),
                type,
                id
        );
    }
}
