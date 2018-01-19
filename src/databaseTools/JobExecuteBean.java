package databaseTools;

import helpers.JavaHelper;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by antiz_000 on 2/21/2016.
 */
@Singleton
@Startup
public class JobExecuteBean {

    @Schedule(minute = "*/1", hour = "*")
    public void execute() throws SQLException, ClassNotFoundException {
        List<Object[]> jobs = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectAllJobs(), null);
        if (jobs != null) {
            for (Object[] job : jobs) {
                BigDecimal id = (BigDecimal) job[0];
                if (!BigDecimal.ONE.equals(job[5]))
                    continue;
                String necessaryForExecute = (String) JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().checkJobTimeOut(), job[0]);
                if ("true".equals(necessaryForExecute)) {
                    JavaHelper.executeJob(id);
                }
            }
        }
    }
}
