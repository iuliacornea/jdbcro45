package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionExample {

    public static void main(String[] args) {

        Connection connection;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Error on retrieving connection: ");
            throw new RuntimeException(e);
        }

        createTableMarketingCampaign(connection);
        initializeMarketingCampaign(connection);
    }

    // create a table
    public static void createTableMarketingCampaign(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists marketing_campaign (" +
                    "id int primary key auto_increment," +
                    "name varchar(200)," +
                    "start_date date," +
                    "budget double);");
        } catch (SQLException e) {
            System.out.println("Error creating table marketing_campaign: ");
            throw new RuntimeException(e);
        }
    }

    // insert a row/multiple rows in a table if the table is empty
    public static void initializeMarketingCampaign(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet countResult = statement.executeQuery("select count(*) from marketing_campaign");
            if (countResult.next() && countResult.getInt(1) == 0) {
                statement.executeUpdate("insert into marketing_campaign (name, start_date, budget) values " +
                        "('Christmas promo', '2022-12-15', 100000), " +
                        "('Valentines Instagram promo', '2023-01-20', 5000), " +
                        "('Easter national campaign', '2023-03-01', 400000);");
                System.out.println("Initialized marketing_campaign successfully");
            } else {
                System.out.println("Marketing campaign already initialized.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // display all values in a table

    // search for a row by a value


}
