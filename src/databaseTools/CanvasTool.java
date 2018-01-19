package databaseTools;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by antiz_000 on 2/17/2016.
 */
public class CanvasTool {
    public static String getSchema() throws SQLException, ClassNotFoundException {
        List<Object[]> points = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectPoints(), null);
        List<Object[]> lines = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectLines(), null);
        List<Object[]> devices = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectDevices(), null);
        StringBuilder sb = new StringBuilder();
        if (points != null) {
            sb.append("var points = [\n");
            for (Object[] p : points) {
                sb.append("{id: ").append(p[0]).append(", x: ").append(p[1]).append(", y: ").append(p[2]).append("},\n");
            }
            sb = sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]\n");
        }
        if (lines != null) {
            sb.append("var lines = [\n");
            for (Object[] l : lines) {
                sb.append("{start: ").append(l[0]).append(", end: ").append(l[1]).append("},\n");
            }
            sb = sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]\n");
        }
        if (devices != null) {
            sb.append("var devices = [\n");
            for (Object[] d : devices) {
                sb.append("{name: '").append(d[0]).append("', x: ").append(d[1]).append(", y: ").append(d[2]).append(", data: '").append(d[3] != null ? d[3] : "value not found").append("'},\n");
            }
            sb = sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]\n");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
