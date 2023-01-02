package if21b151.application.card.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CardRepositoryTest {

    PrintService printService;
    CardRepository cardRepository;
    CardService cardService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        cardRepository = new CardRepositoryImpl();
        cardService = new CardServiceImpl();
    }

    @Test
    void testCountPackages() {
        printService.consoleLog("unitTest", "CardRepository: Count All Packages");
        System.out.println(cardRepository.countPackages());
    }

    @Test
    void testAddNewPackage() {
        printService.consoleLog("unitTest", "CardRepository: Create New Package");
        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);
        cardRepository.addNewPackage(cardPackage);
        System.out.println(cardRepository.countPackages());
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}