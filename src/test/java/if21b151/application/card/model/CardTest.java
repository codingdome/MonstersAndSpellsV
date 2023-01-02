package if21b151.application.card.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    PrintService printService;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
    }

    @Test
    void createAndPrintCard() {
        printService.consoleLog("unitTest", "CardTest: Test Create And Print Card");
        Card card = new Card("card-id-1234567898765432", "RegularSpell", 10.3);
        printService.printCard(card);
        Assertions.assertEquals(10.3, card.getDamage());
        Assertions.assertEquals(ElementType.REGULAR, card.getElementType());
    }

    @Test
    void createAndPrintNewCard() {
        printService.consoleLog("unitTest", "CardTest: Test Create And Print New Card");
        Card card = new Card();
        printService.printCard(card);
        Assertions.assertEquals(0, card.getDamage());
        Assertions.assertEquals(0, card.getDeck());
        Assertions.assertEquals(0, card.getTrade());
    }
}