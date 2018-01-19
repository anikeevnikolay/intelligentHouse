/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseTools;

import helpers.PropertiesHelper;
import helpers.PropertyConsts;
import sqlPackage.SQLContainer;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author antizaiac
 */
public class JDBCUtil {

    private static String driverName = null;
    private static String connectionName = null;
    private static String login = null;
    private static String password = null;
    private static SQLContainer sqlContainer = new SQLContainer();

    static {
        reloadConfig();
    }

    public static SQLContainer getSQLContainer() {
        return sqlContainer;
    }

    public static String getLogin() {
        return login;
    }

    public static void reloadConfig() {
        Properties properties = PropertiesHelper.getProperty(PropertyConsts.CONNECTION_PROPERTIES);
        driverName = properties.getProperty("driver_name");
        connectionName = properties.getProperty("connection_name");
        login = properties.getProperty("login");
        password = properties.getProperty("password");
    }

    public static List<Object[]> executeQuery(String query, Object... params) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(
                    connectionName, login, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            fillParams(preparedStatement, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> resultList = new LinkedList<>();
            int size = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Object[] currentRow = new Object[size];
                for (int i = 0; i < size; i++) {
                    int type = resultSet.getMetaData().getColumnType(i + 1);
                    switch (type) {
                        case Types.VARCHAR: {
                            currentRow[i] = resultSet.getString(i + 1);
                            break;
                        }
                        case Types.BIGINT: {
                            currentRow[i] = resultSet.getBigDecimal(i + 1);
                            break;
                        }
                        case Types.TIMESTAMP_WITH_TIMEZONE: {
                        }
                        case Types.TIMESTAMP: {
                            currentRow[i] = resultSet.getTimestamp(i + 1);
                            break;
                        }
                        case Types.DATE: {
                            currentRow[i] = resultSet.getDate(i + 1);
                            break;
                        }
                        default: {
                            currentRow[i] = resultSet.getString(i + 1);
                        }
                    }
                }
                resultList.add(currentRow);
            }
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } finally {
            try {
                connection.close();
            } catch (SQLException | NullPointerException ex) {

            }
        }
    }

    public static Object executeForObject(String query, Object... params) throws SQLException, ClassNotFoundException {
        List<Object[]> l = executeQuery(query, params);
        return l != null ? l.get(0)[0] : null;
    }

    public static BigDecimal getNewId() throws SQLException, ClassNotFoundException {
        return (BigDecimal) executeForObject(JDBCUtil.getSQLContainer().getId(), null);
    }

    public static List<String[]> executeQueryDB150(String query) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(
                    connectionName, login, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<String[]> resultList = new LinkedList<>();
            int size = resultSet.getMetaData().getColumnCount();

            String[] zeroRow = new String[size];
            for (int i = 0; i < size; i++) {
                zeroRow[i] = resultSet.getMetaData().getColumnName(i + 1);
            }
            resultList.add(zeroRow);
            while (resultSet.next()) {
                String[] currentRow = new String[size];
                for (int i = 0; i < size; i++) {
                    currentRow[i] = resultSet.getString(i + 1);
                }
                resultList.add(currentRow);
            }
            return resultList;
        } finally {
            try {
                connection.close();
            } catch (SQLException | NullPointerException ex) {

            }
        }
    }

    public static void executeDmlOrDdlQuery(String query, Object... params) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(
                    connectionName, login, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            fillParams(preparedStatement, params);
            preparedStatement.execute();
        } finally {
            try {
                connection.close();
            } catch (SQLException | NullPointerException ex) {

            }
        }
    }

    private static void fillParams(PreparedStatement preparedStatement, Object[] params) throws SQLException {
        if (params != null)
            for (int i = 0; i < params.length; i++) {
                Object p = params[i];
                if (p instanceof BigDecimal) {
                    preparedStatement.setBigDecimal(i + 1, (BigDecimal) p);
                } else if (p instanceof java.sql.Date) {
                    preparedStatement.setDate(i + 1, (Date) p);
                } else if (p instanceof String) {
                    preparedStatement.setString(i + 1, (String) p);
                } else if (p instanceof Timestamp) {
                    preparedStatement.setTimestamp(i + 1, (Timestamp) p);
                } else {
                    throw new UnsupportedOperationException("JDBCUtil not supported " + p.getClass().getCanonicalName() + " as param type");
                }
            }
    }
}
