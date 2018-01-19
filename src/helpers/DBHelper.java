package helpers;

import databaseTools.JDBCUtil;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by antiz_000 on 2/24/2016.
 */
public class DBHelper {
    public void dump() throws IOException {
        Properties properties = PropertiesHelper.getProperty(PropertyConsts.DATABASE_PROPERTIES);
        String login = JDBCUtil.getLogin();
        String pgFolder = properties.getProperty("db_path") + "pg_dump";
        String dumpFolder = properties.getProperty("db_dumps");
        String name = "dump_" + new Date().getTime() + ".sql";
//        String name = "1.sql";
        dumpFolder += name;
        Process process;
        ProcessBuilder processBuilder;
        processBuilder = new ProcessBuilder(
                "\"cmd.exe\"", "/c", pgFolder,
                "-h", "localhost", "-U", login, ">", dumpFolder);
        processBuilder.redirectErrorStream(true);
        process = processBuilder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String ll;
        StringBuilder sb = new StringBuilder();
        while ((ll = br.readLine()) != null) {
            sb.append(ll);
        }
        if (!"".equals(sb.toString())) {
            throw new UnsupportedOperationException(sb.toString());
        }
    }

    public void cleanupDumps(String days) {
        throw new UnsupportedOperationException("cleanup of DB dumps not supported yet");
    }
}
