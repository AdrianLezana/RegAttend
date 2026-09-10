package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Esto creará un archivo regattend.db, nos sirve para tener activa la DB
    private static final String URL = "jdbc:sqlite:regattend.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}