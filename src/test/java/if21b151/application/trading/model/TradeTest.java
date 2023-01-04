package if21b151.application.trading.model;

import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    PrintService printService;
    Trade trade;

    @BeforeEach
    void initServices() {
        this.printService = new PrintServiceImpl();
    }

    @Test
    void getUsername() {
        printService.consoleLog("unitTest", "TradeTest: Get Username");
        trade = new Trade("dominik", "tradeId", "cardIdToTrade", "monster", 5.5);
        Assertions.assertEquals("dominik", trade.getUsername());
    }

    @Test
    void setUsername() {
        printService.consoleLog("unitTest", "TradeTest: Set Username");
        trade = new Trade("dominik", "tradeId", "cardIdToTrade", "monster", 5.5);
        trade.setUsername("peter");
        Assertions.assertEquals("peter", trade.getUsername());
    }

    @Test
    void getId() {
        printService.consoleLog("unitTest", "TradeTest: Get Id");
        trade = new Trade("dominik", "tradeId", "cardIdToTrade", "monster", 5.5);
        Assertions.assertEquals("tradeId", trade.getId());
    }

    @Test
    void setId() {
        printService.consoleLog("unitTest", "TradeTest: Set Id");
        trade = new Trade("dominik", "tradeId", "cardIdToTrade", "monster", 5.5);
        trade.setId("tradeID2");
        Assertions.assertEquals("tradeID2", trade.getId());
    }

    @Test
    void newTradeDeal() {
        printService.consoleLog("unitTest", "TradeTest: New Trade Deal");
        trade = new Trade("dominik", "tradeId", "cardIdToTrade", "monster", 5.5);
        Assertions.assertEquals("dominik", trade.getUsername());
        Assertions.assertEquals("tradeId", trade.getId());
        Assertions.assertEquals("cardIdToTrade", trade.getCardToTrade());
        Assertions.assertEquals("monster", trade.getType());
        Assertions.assertEquals(5.5, trade.getMinimumDamage());
    }

}