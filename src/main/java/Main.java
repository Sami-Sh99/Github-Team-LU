//package DB.com.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sami Sh
 * Simple Hello World MySQL Tutorial on how to make JDBC connection, Add and Retrieve Data
 *
 */

public class Main {

    static Connection DBConn = null;
    static PreparedStatement DBPrepareStat = null;

    public static void main(String[] argv) {

        try {
            log("--------Simple DB Tutorial on how to make JDBC connection to MySQL Database locally------------");
            makeJDBCConnection();

            log("\n---------- Adding subject to DB ----------");
            addSubjectToDB("313","Laravel PHP");

            log("\n---------- Let's get Data from DB ----------");
            getDataFromDB();

            DBPrepareStat.close();
            DBConn.close(); // connection close

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            DBConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/technosoft", "root", "");
            if (DBConn != null) {
                log("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    private static void addSubjectToDB(String subject_id, String subject_name) {

        try {
            String insertQueryStatement = "insert into `subject` (`subject_id`,`subject_name`) values (?,?);";

            DBPrepareStat = DBConn.prepareStatement(insertQueryStatement);
            DBPrepareStat.setString(1, subject_id);
            DBPrepareStat.setString(2, subject_name);

            // execute insert SQL statement
            DBPrepareStat.executeUpdate();
            log(subject_name + " added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromDB() {

        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM student";

            DBPrepareStat = DBConn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = DBPrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String sfname = rs.getString("sfname");
                String slname = rs.getString("slname");
                String userid = rs.getString("userid");
                String contact = rs.getString("contact");

                // Simply Print the results
                System.out.format("%s, %s, %s, %s\n", sfname, slname, userid, contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Simple log utility
    private static void log(String string) {
        System.out.println(string);

    }
}
