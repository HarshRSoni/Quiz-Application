package com.quiz.quizapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/quizdb"; // Database URL
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "mypassword";

    public static Connection getConnection() {
        Connection dbConnection = null;

        try {
            dbConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Connection to the database established successfully.");
        }

        catch (SQLException sqlException) {
            System.err.println("Failed to connect to the database.");
            sqlException.printStackTrace();
        }

        return dbConnection;
    }
}