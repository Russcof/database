package com.example;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseService dbService = new DatabaseService();

        System.out.println("____________________________");

        String login = "gal";
        Users result = dbService.getUser(login);
        System.out.println("первый запрос: " + result);

        System.out.println("____________________________");

        Map<String, String> newUser = new HashMap<>();
        newUser.put("login","customer");
        newUser.put("password","qwerty");
        newUser.put("email","customer@example.com");
        int number = dbService.postLogin(newUser);
        if (number != 0) {
            System.out.println("второй запрос: " + number);
        } else {
            System.out.println("User not found.");
        }
    }
}
