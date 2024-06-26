package com.pepe.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:jtds:sqlserver://mssql-157912-0.cloudclusters.net:18171/Taqueria";
    private static final String USER = "Gremio";
    private static final String PASSWORD = "123qwe123QWE-";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el controlador JDBC");
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}