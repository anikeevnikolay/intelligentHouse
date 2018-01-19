package helpers;

import compile.ClassLoadHelper;
import databaseTools.JDBCUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by antiz_000 on 2/23/2016.
 */
public class JavaHelper {
    public static void executeJob(BigDecimal id) throws SQLException, ClassNotFoundException {
        try {
            Object[] job = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectJob(), id).get(0);
            if ("SQL".equals(job[5])) {
                JDBCUtil.executeDmlOrDdlQuery((String) job[1], null);
            }
            if ("Java".equals(job[5])) {
                executeJava((String) job[1]);
            }
            updateExecuteDate(id);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement elem : e.getStackTrace()) {
                sb.append(elem.toString()).append("\n");
            }
            saveErrorRecord(id, e.getClass().getName() + ": " + e.getMessage(), sb.substring(0, sb.length() - 1));
            updateExecuteDate(id);
        }
    }

    private static void updateExecuteDate(BigDecimal jobId) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().updateJobLastExecute(), jobId);
    }

    public static void saveErrorRecord(BigDecimal jobId, String errorName, String errorText) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertErrorRecord(), jobId, errorText, errorName);
    }

    private static void executeJava(String params) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String className = null;
        String methodName = null;
        String[] args = null;
        for (String s : params.split(";")) {
            if (s.substring(0, s.indexOf("=")).toLowerCase().equals("class"))
                className = s.substring(s.indexOf("=") + 1);
            if (s.substring(0, s.indexOf("=")).toLowerCase().equals("method"))
                methodName = s.substring(s.indexOf("=") + 1);
            if (s.substring(0, s.indexOf("=")).toLowerCase().equals("params")) {
                String tmp = s.substring(s.indexOf("=") + 1);
                args = tmp.split(",");
            }
        }
        Class[] argsType = null;
        if (args != null) {
            argsType = new Class[args.length];
            Arrays.fill(argsType, String.class);
        }
        if (className == null || methodName == null)
            throw new IllegalStateException("class or method not specified");
        Class clas = ClassLoadHelper.loadClassByName(className);
        Method m = clas.getMethod(methodName, argsType);
        m.invoke(clas.newInstance(), args);
    }
}
