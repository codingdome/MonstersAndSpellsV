package if21b151.application.trading.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.trading.model.Trade;
import if21b151.application.trading.repository.TradeRepository;
import if21b151.application.trading.repository.TradeRepositoryImpl;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.application.user.service.UserServiceImpl;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TradeServiceTest {

    PrintService printService;
    TradeService tradeService;
    TradeRepository tradeRepository;
    UserService userService;
    CardService cardService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        tradeService = new TradeServiceImpl();
        tradeRepository = new TradeRepositoryImpl();
        userService = new UserServiceImpl();
        cardService = new CardServiceImpl();
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

    @Test
    void deleteTrade() {
        printService.consoleLog("unitTest", "TradeService: Delete trade");
        User user = new User();
        user.setUsername("username");
        Trade trade = new Trade(user.getUsername(), "tradeID1234554321", "1cb6ab86-bdb2-47e5-b6e4-68c5ab389334", "monster", 15.0);
        tradeService.addTrade(user, trade);
        printService.printTrade(tradeRepository.getTradeByID("tradeID1234554321"));
        tradeService.deleteTrade(user, "tradeID1234554321");
        Assertions.assertEquals(null, tradeRepository.getTradeByID("tradeID1234554321"));
    }

    @Test
    void trade() {
        printService.consoleLog("unitTest", "TradeService: Full trade");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        userService.create(user2);

        userService.login(user);
        userService.login(user2);

        printService.printUser(userService.get(user));
        printService.printUser(userService.get(user2));

        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);

        Card[] cards2 = new Card[5];
        cards2[0] = new Card("id6", "RegularSpell", 10);
        cards2[1] = new Card("id7", "WaterTroll", 10);
        cards2[2] = new Card("id8", "WaterSpell", 30);
        cards2[3] = new Card("id9", "FireSpell", 40);
        cards2[4] = new Card("id10", "RegularTroll", 50);
        CardPackage cardPackage2 = cardService.cardsToCardPackage(cards2);

        cardService.addNewPackage(cardPackage);
        cardService.acquirePackage(user);
        cardService.addNewPackage(cardPackage2);
        cardService.acquirePackage(user2);

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());

        Trade trade = new Trade(user.getUsername(), "tradeID", "id2", "spell", 5.0);
        printService.printTrade(trade);
        tradeService.addTrade(user, trade);

        Assertions.assertEquals(0, tradeService.trade(user2, "tradeID", "id6"));

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username2", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username", cardService.getCardByID("id6").getUsername());
    }

    @Test
    void tradeWithYourself() {
        printService.consoleLog("unitTest", "TradeService: Trade with yourself");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        userService.create(user2);

        userService.login(user);
        userService.login(user2);

        printService.printUser(userService.get(user));
        printService.printUser(userService.get(user2));

        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);

        Card[] cards2 = new Card[5];
        cards2[0] = new Card("id6", "RegularSpell", 10);
        cards2[1] = new Card("id7", "WaterTroll", 10);
        cards2[2] = new Card("id8", "WaterSpell", 30);
        cards2[3] = new Card("id9", "FireSpell", 40);
        cards2[4] = new Card("id10", "RegularTroll", 50);
        CardPackage cardPackage2 = cardService.cardsToCardPackage(cards2);

        cardService.addNewPackage(cardPackage);
        cardService.acquirePackage(user);
        cardService.addNewPackage(cardPackage2);
        cardService.acquirePackage(user2);

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());

        Trade trade = new Trade(user.getUsername(), "tradeID", "id2", "spell", 5.0);
        printService.printTrade(trade);
        tradeService.addTrade(user, trade);

        Assertions.assertEquals(2, tradeService.trade(user, "tradeID", "id1"));

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());
    }

    @Test
    void tradeBadCard() {
        printService.consoleLog("unitTest", "TradeService: Trade with bad card (to low damage)");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        userService.create(user2);

        userService.login(user);
        userService.login(user2);

        printService.printUser(userService.get(user));
        printService.printUser(userService.get(user2));

        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);

        Card[] cards2 = new Card[5];
        cards2[0] = new Card("id6", "RegularSpell", 10);
        cards2[1] = new Card("id7", "WaterTroll", 10);
        cards2[2] = new Card("id8", "WaterSpell", 30);
        cards2[3] = new Card("id9", "FireSpell", 40);
        cards2[4] = new Card("id10", "RegularTroll", 50);
        CardPackage cardPackage2 = cardService.cardsToCardPackage(cards2);

        cardService.addNewPackage(cardPackage);
        cardService.acquirePackage(user);
        cardService.addNewPackage(cardPackage2);
        cardService.acquirePackage(user2);

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());

        Trade trade = new Trade(user.getUsername(), "tradeID", "id2", "spell", 50.0);
        printService.printTrade(trade);
        tradeService.addTrade(user, trade);

        Assertions.assertEquals(3, tradeService.trade(user2, "tradeID", "id9"));

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());
    }

    @Test
    void tradeBadCard2() {
        printService.consoleLog("unitTest", "TradeService: Trade with bad card (wrong type)");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        userService.create(user2);

        userService.login(user);
        userService.login(user2);

        printService.printUser(userService.get(user));
        printService.printUser(userService.get(user2));

        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);

        Card[] cards2 = new Card[5];
        cards2[0] = new Card("id6", "RegularSpell", 10);
        cards2[1] = new Card("id7", "WaterTroll", 10);
        cards2[2] = new Card("id8", "WaterSpell", 30);
        cards2[3] = new Card("id9", "FireSpell", 40);
        cards2[4] = new Card("id10", "RegularTroll", 50);
        CardPackage cardPackage2 = cardService.cardsToCardPackage(cards2);

        cardService.addNewPackage(cardPackage);
        cardService.acquirePackage(user);
        cardService.addNewPackage(cardPackage2);
        cardService.acquirePackage(user2);

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());

        Trade trade = new Trade(user.getUsername(), "tradeID", "id2", "monster", 5.0);
        printService.printTrade(trade);
        tradeService.addTrade(user, trade);

        Assertions.assertEquals(3, tradeService.trade(user2, "tradeID", "id9"));

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());
    }

    @Test
    void tradeWrongTradeID() {
        printService.consoleLog("unitTest", "TradeService: Trade with wrong trade id");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        userService.create(user2);

        userService.login(user);
        userService.login(user2);

        printService.printUser(userService.get(user));
        printService.printUser(userService.get(user2));

        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);

        Card[] cards2 = new Card[5];
        cards2[0] = new Card("id6", "RegularSpell", 10);
        cards2[1] = new Card("id7", "WaterTroll", 10);
        cards2[2] = new Card("id8", "WaterSpell", 30);
        cards2[3] = new Card("id9", "FireSpell", 40);
        cards2[4] = new Card("id10", "RegularTroll", 50);
        CardPackage cardPackage2 = cardService.cardsToCardPackage(cards2);

        cardService.addNewPackage(cardPackage);
        cardService.acquirePackage(user);
        cardService.addNewPackage(cardPackage2);
        cardService.acquirePackage(user2);

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());

        Trade trade = new Trade(user.getUsername(), "tradeID", "id2", "spell", 50.0);
        printService.printTrade(trade);
        tradeService.addTrade(user, trade);

        Assertions.assertEquals(1, tradeService.trade(user2, "wrongTradeID", "id9"));

        printService.printCard(cardService.getCardByID("id2"));
        printService.printCard(cardService.getCardByID("id6"));

        Assertions.assertEquals("username", cardService.getCardByID("id2").getUsername());
        Assertions.assertEquals("username2", cardService.getCardByID("id6").getUsername());
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}