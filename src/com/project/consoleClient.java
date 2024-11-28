package com.project;

import java.sql.*;

public class consoleClient {
    public static String url = "";
    public static String username = "h123";
    public static String password = "";

    public static void main(String[] args) throws SQLException {
//        Connection con;
//        try {
//            con = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
//
//        String sql = "INSERT INTO Books VALUES(?,?,?,?, 0)";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//
//        pstmt.clearParameters();
//        pstmt.setInt(1, 1);
//        pstmt.setInt(2, 2);
//        pstmt.setInt(3, 3);
//        pstmt.setInt(4, 4);
//
//        int numRows = pstmt.executeUpdate();

        System.out.println("Welcome to sales system!\n");
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operations for administrator");
        System.out.println("2. Operations for salesperson");
        System.out.println("3. Operation for manager");
        System.out.println("4. Exit the system");

        User user = new user;
    }
}
