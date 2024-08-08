package com.example;

import java.sql.*;

public class DatabaseService {
    private final String url = "jdbc:postgresql://10.201.72.37:5432/pg_database";
    private final String username = "itguy";
    private final String password = "2024";

    public Users getUser(String login) throws SQLException {

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();
            String query = "SELECT us.login, password, email, date FROM users as us " +
                    "JOIN emails as up ON us.login = up.login WHERE us.login = '" + login + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Timestamp date = resultSet.getTimestamp("date");
                return new Users(login, password, email, date);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }


    public int insertUser(Users userNew) {
        String queryInsert = "INSERT INTO users(login, password) VALUES (?,?);" + "\n" + "INSERT INTO emails(login, email) VALUES (?,?)";
        try (Connection connection2 = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection2.prepareStatement(queryInsert)) {

            ps.setString(1, userNew.getLogin());
            ps.setString(2, userNew.getPassword());
            ps.setString(3, userNew.getLogin());
            ps.setString(4, userNew.getEmail());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}