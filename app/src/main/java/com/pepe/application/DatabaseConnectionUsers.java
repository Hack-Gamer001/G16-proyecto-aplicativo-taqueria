package com.pepe.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionUsers {

    private static final String URL = "jdbc:jtds:sqlserver://mssql-157912-0.cloudclusters.net:18171/Taqueria";
    private static final String USER = "Gremio";
    private static final String PASSWORD = "123qwe123QWE-";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el controlador JDBC", e);
        }
    }

    public static boolean verificarUsuario(String rol, String nombre, String contraseña) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM Usuarios WHERE Rol = ? AND Nombre = ? AND Contraseña = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rol);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, contraseña);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Devuelve true si hay al menos un resultado
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection, preparedStatement, resultSet);
        }
    }

    public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método adicional para verificar la existencia de usuarios en la base de datos
    public static void verificarUsuariosEnBaseDeDatos() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM Usuarios";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            System.out.println("Usuarios en la base de datos:");
            while (resultSet.next()) {
                System.out.println("UsuarioID: " + resultSet.getInt("UsuarioID") +
                        ", Rol: " + resultSet.getString("Rol") +
                        ", Nombre: " + resultSet.getString("Nombre") +
                        ", Contraseña: " + resultSet.getString("Contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, preparedStatement, resultSet);
        }
    }
}
