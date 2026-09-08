package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL de conexión para SQLite local (ajustar si usas MySQL)
    private static final String URL = "jdbc:sqlite:regattend.db";
    private static Connection instance = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            instance = DriverManager.getConnection(URL);
        }
        return instance;
    }
}
