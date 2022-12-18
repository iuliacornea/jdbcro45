package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        try {
            // display all products
            Statement statement = DatabaseConnection.getConnection().createStatement();

//            int rowCount = statement.executeUpdate("insert into product (name, product_type, price, quantity) values " +
//                    "('Poison Ivy', 'Flower', 20.5, 100)," +
//                    "('Bonsai', 'Potted plant', 100, 20);");
//            System.out.println("Row count = " + rowCount);

            // set de rânduri
            ResultSet productsSet = statement.executeQuery("SELECT * FROM product");
            while(productsSet.next()) {
                System.out.print("Product: \t" + productsSet.getString(2));
                System.out.print(" with price: \t" + productsSet.getDouble(4));
                System.out.println(" and quantity: \t" + productsSet.getInt(5));
            }

            ResultSet mostExpensiveProduct = statement.executeQuery(
                    "SELECT * FROM product WHERE price = (SELECT MAX(price) FROM product) ");
            mostExpensiveProduct.next();
            System.out.println("Most expensive product is " + mostExpensiveProduct.getString(2)
                + "with a price of " + mostExpensiveProduct.getDouble(4));

            displayAllProductsFrom("Magnolia");

            displayProductsWithinRange(10, 20);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void displayAllProductsFrom(String supplierName) throws SQLException {
        Statement statement = DatabaseConnection.getConnection().createStatement();
        ResultSet suppliersWithProducts = statement.executeQuery("select supplier.name, product.name  from supplier " +
                "join supplier_product_connection as con on supplier.id = con.supplier_id " +
                "join product on con.product_id = product.id " +
                "where supplier.name = '" + supplierName + "';");
        while(suppliersWithProducts.next()) {
            System.out.println(suppliersWithProducts.getString(1) + " " + suppliersWithProducts.getString(2));
        }
    }

    // all flowers that are more than Quantity Q and cost less than Price P
    static void displayProductsWithinRange(int quantity, double price) throws SQLException {
        Statement s = DatabaseConnection.getConnection().createStatement();
        ResultSet products = s.executeQuery("select name, price, quantity from product " +
                "where quantity >= " + quantity + " " +
                "and price <= " + price + ";");
        while (products.next()) {
            System.out.println(products.getString(1) + " " +
                    products.getDouble(3) + "RON " +
                    products.getInt(2) + " buc.");
        }
    }

}
