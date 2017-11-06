package com.skaliy.dbc;

import org.intellij.lang.annotations.Language;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class Connector {
    private Connection connection;

    public Connector(String driverClass, String driverUrl, String url, String user, String password) {
        try {
            Class.forName(driverClass);

            try {
                connection = DriverManager.getConnection(
                        "jdbc:" + driverUrl + "://" + url,
                        user,
                        password);
            } catch (PSQLException exception) {
                connection = null;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(@Language("SQL") String sql) {
        try {
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[][] queryResult(@Language("SQL") String sql) {
        try {
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = statement.executeQuery(sql);

            int col = resultSet.getMetaData().getColumnCount();
            resultSet.last();
            int row = resultSet.getRow();
            String[][] result = new String[row][col];
            resultSet.beforeFirst();

            int i = 0;
            while (resultSet.next()) {
                for (int j = 0; j < col; j++) {
                    String val = resultSet.getString(j + 1);
                    if (val != null)
                        result[i][j] = val;
                }
                i++;
            }

            resultSet.close();
            statement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[][]{};

    }

    public boolean isConnected() {
        return connection != null;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}