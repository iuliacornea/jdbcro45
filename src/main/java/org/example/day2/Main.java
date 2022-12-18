package org.example.day2;

import org.example.day1.DatabaseConnection;
import org.example.day2.dao.MarketingCampaign;
import org.example.day2.dao.MarketingCampaignDAO;

import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        MarketingCampaignDAO marketingCampaignDAO = new MarketingCampaignDAO(DatabaseConnection.getConnection());

        marketingCampaignDAO.createTable();
        marketingCampaignDAO.initialize();
        MarketingCampaign myCampaign = new MarketingCampaign(null, "SDA marketing campaign", Date.valueOf("2022-12-18"), 5000d );
//        marketingCampaignDAO.create(myCampaign);
//        marketingCampaignDAO.createWithPeparedStatemet(new MarketingCampaign(null, "New Year\'s Campaign", Date.valueOf("2022-12-01"), 1000d));
        System.out.println("All marketing campaigns");
        System.out.println(marketingCampaignDAO.findAll());
        System.out.println("Search by name 'Year'");
        System.out.println(marketingCampaignDAO.searchByName("Year"));
        System.out.println("Search between 2023-01-01 and 2023-12-31");
        System.out.println(marketingCampaignDAO.searchByDate(Date.valueOf("2023-01-01"), Date.valueOf("2023-12-31")));
    }
}
