package org.example.day1;


import java.sql.*;

public class JdbcDirectConnectionExample {
    public static void main(String[] args) {

        try {
            System.out.println("Trying to connect to database.");
            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flower_shop",
                    "root",
                    "root");
            System.out.println("Connection to database successful.");
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while(resultSet.next()) {
                String name = resultSet.getString(2);
                Integer quantity = resultSet.getInt(5);
                Double price = resultSet.getDouble(4);
                System.out.println("Produsul " + name + " cu pretul " + price + " maxim " + quantity + " bucati");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}