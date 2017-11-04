package com.dbc;

import org.intellij.lang.annotations.Language;

import java.sql.*;

public class Connector {
    private Connection connection;
    private Statement statement;

    public Connector(String driverClass, String driverUrl, String url, String user, String password) {
        try {
            Class.forName(driverClass);

            connection = DriverManager.getConnection(
                    "jdbc:" + driverUrl + "://" + url,
                    user,
                    password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(@Language("SQL") String sql) {
        try {
            statement = connection.createStatement(
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
            statement = connection.createStatement(
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
}
