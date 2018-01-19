package databaseTools;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by antiz_000 on 3/5/2016.
 */
public class StatisticTool {
    public static String prepareValueForJavaScript(String query, Object... params) throws ClassNotFoundException {

        List<Object[]> list = null;
        try {
            list = JDBCUtil.executeQuery(query, params);
        } catch (SQLException e) {
            return "SQLException,1,red;check SQL query,0,white";
        }
        if (list == null) {
            return "no result,1,gray;check SQL query,0,white";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Object[] o : list) {
                try {
                    sb.append(o[0]).append(",").append(o[1]).append(",").append(o[2]).append(";");
                } catch (IndexOutOfBoundsException e) {
                    return "incorrect result,1,red;check SQL query,0,white";
                }
            }
            return sb.toString().substring(0, sb.length() - 1);
        }
    }

    public static BigDecimal createNewStatistic() throws SQLException, ClassNotFoundException {
        BigDecimal id = JDBCUtil.getNewId();
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertNewStatistic(), id, DBConsts.RADIAL_STATISTIC_TYPE_ID);
        return id;
    }

    public static Object[] getStatisticParams(BigDecimal id) throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectStatistic(), id).get(0);
    }

    public static void updateStatistic(BigDecimal id, String name, String query, String width, String height, BigDecimal typeId) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().updateStatistic(),
                name,
                query,
                new BigDecimal(width),
                new BigDecimal(height),
                typeId,
                id
        );
    }
}
