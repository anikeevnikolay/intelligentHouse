package databaseTools;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by antiz_000 on 3/13/2016.
 */
public class DB150Tool {

    public static String idBacklight(String oldText) throws SQLException, ClassNotFoundException {
        StringBuilder text = new StringBuilder(oldText);
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < text.length()) {
            if (Character.isDigit(text.charAt(i))) {
                int startNum = i;
                while (Character.isDigit(text.charAt(i))) {
                    sb.append(text.charAt(i));
                    i++;
                    if (i == text.length())
                        break;
                }
                BigDecimal currId = new BigDecimal(sb.toString());
                Object currResult = JDBCUtil.executeForObject("select get_name_by_id(" + currId + ")", null);
                if (currResult != null)
                    text.replace(startNum, i, currId + " /*" + currResult + "*/");
                sb.delete(0, sb.length());
            } else
                i++;
        }
        return text.toString();
    }
}
