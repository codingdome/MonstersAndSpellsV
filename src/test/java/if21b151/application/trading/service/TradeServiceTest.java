package if21b151.application.trading.service;

import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.application.user.service.UserServiceImpl;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TradeServiceTest {

    PrintService printService;
    TradeService tradeService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        tradeService = new TradeServiceImpl();
    }

    @Test
    void addTrade() {
        printService.consoleLog("unitTest", "TradeService: Add Trade");
        User user = new User();
        user.setUsername("username");
        Trade trade = new Trade(user.getUsername(), "6cd85277-4590-49d4-b0cf-ba0a921faad0", "1cb6ab86-bdb2-47e5-b6e4-68c5ab389334", "monster", 15.0);
        tradeService.addTrade(user, trade);
        for (Trade tradeGet : tradeService.getTrades()) {
            printService.printTrade(tradeGet);
        }
    }

    @Test
    void getTrades() {
        printService.consoleLog("unitTest", "TradeService: Get Trades");
        User user = new User();
        user.setUsername("username");
        Trade trade = new Trade(user.getUsername(), "6cd85277-4590-49d4-b0cf-ba0a921faad0", "1cb6ab86-bdb2-47e5-b6e4-68c5ab389334", "monster", 15.0);
        Trade trade2 = new Trade(user.getUsername(), "83485277-4590-49d4-b0cf-ba03121faad0", "1cb6ab86-bdb2-47e5-b6e4-68c5ab389334", "spell", 35.0);
        tradeService.addTrade(user, trade);
        tradeService.addTrade(user, trade2);
        for (Trade tradeGet : tradeService.getTrades()) {
            printService.printTrade(tradeGet);
        }
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}