package databaseTools;

import helpers.PropertiesHelper;
import helpers.PropertyConsts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by antiz_000 on 2/19/2016.
 */
public class SessionTool {

    /*create new session with specified params*/
    public static void initSession(BigDecimal id, String ip, String addInfo, int i) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertSession(),
                id,
                ip,
                addInfo,
                BigDecimal.valueOf(i)
        );
    }

    public static List<Object[]> getActiveSessions() throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectSessions(), null);
    }

    public static List<Object[]> getDisabledSessions() throws SQLException, ClassNotFoundException {
        return JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectFailedSessions(), null);
    }

    /* move session to "disable" */
    public static void dropSession(BigDecimal id) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().deleteSession(), id);
    }

    /* check that current session is actual active */
    public static boolean checkSession(BigDecimal id) throws SQLException, ClassNotFoundException {
        List<Object[]> list = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectSessionByIdAndStatus(), id);
        return list != null;
    }

    private static boolean checkUser(String login, String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.LOGIN_PROPERTIES);
        boolean passEquals = compareHash(pass, pr.getProperty("pass_hash"));
        boolean loginEquals = login.equals(pr.getProperty("user"));
       // return "admin".equals(login) && "1".equals(pass);
        return passEquals && loginEquals;
    }

    private static String generateMd5Hash(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = text.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] result = md.digest(bytesOfMessage);
        return new String(result);
    }

    public static void saveHashInProperties(String hash) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.LOGIN_PROPERTIES);
        pr.setProperty("pass_hash", generateMd5Hash(hash));
        pr.store(new FileOutputStream(PropertyConsts.LOGIN_PROPERTIES), "last edit");
    }

    private static boolean compareHash(String pass, String hash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String passHash = generateMd5Hash(pass);
        return hash.equals(passHash);
    }

    /**
     * check user, create session
     *
     * @return session_id or null
     */
    public static BigDecimal checkUser(String login, String pass, String ip, String addInfo) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().checkIpAddress(), ip) != null)
            return null;
        if (checkUser(login, pass)) {
            BigDecimal id = JDBCUtil.getNewId();
            initSession(id, ip, addInfo, 1);
            JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().dropErrorSessionsByIp(), ip);
            return id;
        }
        initSession(JDBCUtil.getNewId(), ip, addInfo, 3);
        BigDecimal count = (BigDecimal) JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().countFailedAttempts(), ip);
        if (count.compareTo(BigDecimal.valueOf(3)) > 0) {
            JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().insertIpInBlackList(), ip);
        }
        return null;
    }

    /* update last visit*/
    public static void updateSession(BigDecimal id) throws SQLException, ClassNotFoundException {
        JDBCUtil.executeDmlOrDdlQuery(JDBCUtil.getSQLContainer().updateSession(), id);
    }
}
