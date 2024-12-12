package com.project;

import java.sql.*;

public class consoleClient {
    public static String url = "";
    public static String username = "h123";
    public static String password = "";

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to sales system!\n");
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operations for administrator");
        System.out.println("2. Operations for salesperson");
        System.out.println("3. Operation for manager");
        System.out.println("4. Exit the system");

        User user = new User();
    }
}
