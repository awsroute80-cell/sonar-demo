package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class BadCode {

    //Hardcoded credentials (Security Vulnerability)
    private static final String DB_PASSWORD = "superSecret123";

    public static void main(String[] args) {
        BadCode bc = new BadCode();
        bc.doSomething();
        bc.infiniteLoop(); //Bug: infinite loop
    }

    //Long method (Code Smell)
    public void doSomething() {
        System.out.println("Doing something...");
        int a = 10;
        int b = 20;
        int c = a + b;

        //Dead code (Unreachable condition)
        if (false) {
            System.out.println("This will never run");
        }

        //Duplicate logic (Code Smell)
        int x = 5;
        int y = 10;
        int z = x + y;
        System.out.println("Sum: " + z);

        //Security Hotspot: Using Runtime exec
        try {
            Runtime.getRuntime().exec("ls"); // SonarQube will warn
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done: " + c);
    }

    //Bug: Infinite loop without exit condition
    public void infiniteLoop() {
        int i = 0;
        while (true) {
            i++;
            if (i > 1000000) {
                System.out.println("Still looping... " + i);
            }
        }
    }

    //Security Vulnerability: building SQL unsafely
    public void insecureDbAccess(String userInput) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "root", DB_PASSWORD);
            String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
            // Imagine executing query here: conn.createStatement().executeQuery(query);
            System.out.println("Query: " + query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
