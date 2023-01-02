package if21b151.application.card.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {
    PrintService printService;
    CardService cardService;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        cardService = new CardServiceImpl();
    }

    @Test
    void testGetPlotCard() {
        printService.consoleLog("unitTest", "CardService: Get Plot Card");
        Card card = cardService.getPlotCard();
        printService.printCard(card);
        Assertions.assertEquals("0", card.getName());
    }

    @Test
    void testCreatePackage() {
        Card[] cards = new Card[5];
        cards[0] = new Card("id1", "RegularSpell", 10);
        cards[1] = new Card("id2", "WaterTroll", 10);
        cards[2] = new Card("id3", "WaterSpell", 30);
        cards[3] = new Card("id4", "FireSpell", 40);
        cards[4] = new Card("id5", "RegularTroll", 50);
        CardPackage cardPackage = cardService.cardsToCardPackage(cards);
        for (Card card : cardPackage.getPackageCards()) {
            printService.printCard(card);
        }
    }
}