package com.example.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * BadCode.java
 * This class intentionally contains poor coding practices and vulnerabilities
 * to demonstrate SonarQube analysis.
 * It also starts a Jetty server to expose an HTTP endpoint for a demo deployment.
 */
public class BadCode {

    //Hardcoded credentials (Security Vulnerability)
    private static final String DB_PASSWORD = "superSecret123";

    public static void main(String[] args) throws Exception {
        //Start a simple Jetty HTTP server on port 8081
        Server server = new Server(8081);
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(HelloServlet.class, "/");
        server.setHandler(handler);

        System.out.println("Starting Jetty server on http://0.0.0.0:8081");
        server.start();

        // Keep some "bad code" running so SonarQube has something to analyze
        BadCode bc = new BadCode();
        bc.doSomething(); // contains smells and issues

        // BUG: infinite loop (intentional for SonarQube demo, won't block Jetty server)
        new Thread(() -> bc.infiniteLoop()).start();

        // Join Jetty server
        server.join();
    }

    //A simple servlet to return a demo response
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            try {
                resp.setContentType("text/plain");
                resp.getWriter().println("Hello from BadCode app deployed via Jenkins & Docker!");
                resp.getWriter().println("This app contains intentional bugs for SonarQube analysis.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    //Security Vulnerability: SQL Injection (unsafe query)
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
