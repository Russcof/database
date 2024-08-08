package com.example;

import java.sql.*;
import java.util.Map;

public class DatabaseService {
    String url = "jdbc:postgresql://10.201.72.37:5432/pg_database";
    String username = "itguy";
    String password = "2024";
    Connection connection;

    public Users getUser(String login) throws SQLException {

        try {
            connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            String query = "SELECT us.login, password, email, date FROM users as us " +
                    "JOIN emails as up ON us.login = up.login WHERE us.login = '" + login + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Timestamp date = resultSet.getTimestamp("date");
                return new Users(login, password, email, date);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public int postLogin( Map<String, String> userNew) {

        try (Connection connection2 = DriverManager.getConnection(url, username, password)) {

            PreparedStatement psUsers = connection2.prepareStatement("INSERT INTO users(login, password, date) VALUES (?,?,?);");
            PreparedStatement psEmail = connection2.prepareStatement("INSERT INTO emails(login, email) VALUES (?,?)");

            psUsers.setString(1, userNew.get("login"));
            psUsers.setString(2, userNew.get("password"));
            psUsers.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            psEmail.setString(1, userNew.get("login"));
            psEmail.setString(2, userNew.get("email"));

            return psUsers.executeUpdate() + psEmail.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}





