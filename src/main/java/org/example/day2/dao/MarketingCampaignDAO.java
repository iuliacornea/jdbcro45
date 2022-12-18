package org.example.day2.dao;

import java.nio.channels.MembershipKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarketingCampaignDAO {

    private final Connection connection;

    public MarketingCampaignDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
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

    public void initialize() {
        try {
            System.out.println("Preparing to initialize table marketing_campaign.");
            Statement statement = connection.createStatement();
            ResultSet countResult = statement.executeQuery("select count(*) from marketing_campaign");
            if (countResult.next() && countResult.getInt(1) == 0) {
                statement.executeUpdate("insert into marketing_campaign (name, start_date, budget) values " +
                        "('Christmas promo', '2022-12-15', 100000), " +
                        "('Valentines Instagram promo', '2023-01-20', 5000), " +
                        "('Easter national campaign', '2023-03-01', 400000);");
                System.out.println("Initialized marketing_campaign successfully");
            } else {
                System.out.println("Table marketing_campaign already initialized.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(MarketingCampaign marketingCampaign) {
        try {
            System.out.println("Preparing to insert " + marketingCampaign.toString());
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into marketing_campaign (name, start_date, budget) values " +
                    "('" + marketingCampaign.getName() + "', " +
                    "'" + marketingCampaign.getStartDate() + "', " +
                    "" + marketingCampaign.getBudget() + ");");
            System.out.println("Inserted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createWithPeparedStatemet(MarketingCampaign marketingCampaign) {
        try {
            System.out.println("Preparing to insert " + marketingCampaign.toString());
            PreparedStatement preparedStatement = connection.prepareStatement("insert into marketing_campaign (name, start_date, budget) values (?, ?, ?);");
            preparedStatement.setString(1, marketingCampaign.getName());
            preparedStatement.setDate(2, marketingCampaign.getStartDate());
            preparedStatement.setDouble(3, marketingCampaign.getBudget());
            preparedStatement.executeUpdate();
            System.out.println("Inserted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    // findAll <- return a List/Set of MarketingCampaign
    public List<MarketingCampaign> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from marketing_campaign;");
            List<MarketingCampaign> marketingCampaignList = new ArrayList<>();
            while (resultSet.next()) {
                MarketingCampaign mk = new MarketingCampaign(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDouble(4));
                marketingCampaignList.add(mk);
            }
            return marketingCampaignList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // search by name <- returnează toate marketing campaign care includ în numele lor, numele dat de utilizator
    // searchByName("Year") -> ["New Year's Campaign", "2023 Year Campaign"]
    // !!! folosiți pentru implementare PreparedStatement
    public List<MarketingCampaign> searchByName(String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from marketing_campaign where name like ?;");
        preparedStatement.setString(1, "%" + name + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<MarketingCampaign> marketingCampaignList = new ArrayList<>();
        while (resultSet.next()) {
            MarketingCampaign mk = new MarketingCampaign(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getDouble(4));
            marketingCampaignList.add(mk);
        }
        return marketingCampaignList;
    }

    // search by date (high_end, low_end) <- returnează toate marketing campaign care au start_date între datele date de utilizator
    // searchBetween(2022-12-01, 2022-12-31) -> toate marketing campaigns care încep în decembrie 2022
    // !!! folosiți pentru implementare PreparedStatement
    public List<MarketingCampaign> searchByDate(Date low_end, Date high_end) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from marketing_campaign where start_date >= ? and start_date <= ?;");
            statement.setDate(1, low_end);
            statement.setDate(2, high_end);
            ResultSet resultSet = statement.executeQuery();
            List<MarketingCampaign> marketingCampaignList = new ArrayList<>();
            while (resultSet.next()) {
                MarketingCampaign mk = new MarketingCampaign(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDouble(4));
                marketingCampaignList.add(mk);
            }
            return marketingCampaignList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
