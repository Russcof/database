package com.example;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseService dbService = new DatabaseService();

        System.out.println("____________________________");

        String login = "customer_2";
        Users result = dbService.getUser(login);
        System.out.println("первый запрос: " + result);

        System.out.println("____________________________");
        Users newUser = new Users();
        newUser.setLogin("customer_9");
        newUser.setPassword("qwertyu48111");
        newUser.setEmail("customer3366@example.com");
        int number = dbService.insertUser(newUser);
        if (number != 0) {
            System.out.println("второй запрос: " + number);
        } else {
            System.out.println("User not found.");
        }
    }
}