package if21b151.application.gameengine.battlelogic;

import if21b151.application.card.model.Card;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleLogicServiceTest {

    PrintService printService;
    BattleLogicService battleLogicService;
    CardService cardService;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        battleLogicService = new BattleLogicServiceImpl();
        cardService = new CardServiceImpl();
    }

    @Test
    void fightTwoMixedCards() {
        printService.consoleLog("unitTest", "BattleLogicService: fight two mixed cards");
        Card a = new Card("ID1", "WaterKraken", 40);
        Card b = new Card("ID2", "FireSpell", 20);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    @Test
    void fightTwoMonsterCards() {
        printService.consoleLog("unitTest", "BattleLogicService: fight two monster cards");
        Card a = new Card("ID1", "WaterDragon", 10);
        Card b = new Card("ID2", "FireElve", 50);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(b, battleLogicService.fightTwoCards(a, b));
    }

    //
    @Test
    void fightTwoSpellCards() {
        printService.consoleLog("unitTest", "BattleLogicService: fight two spell cards");
        Card a = new Card("ID1", "WaterSpell", 10);
        Card b = new Card("ID2", "FireSpell", 10);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    @Test
    void fightGoblinVsDragon() {
        printService.consoleLog("unitTest", "BattleLogicService: fight goblin vs dragon");
        printService.consoleLog("message", "Goblins are too afraid of dragons to attack");
        Card a = new Card("ID1", "WaterDragon", 10);
        Card b = new Card("ID2", "WaterGoblin", 100);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    //
    @Test
    void fightWizzardVsOrk() {
        printService.consoleLog("unitTest", "BattleLogicService: fight wizzard vs ork");
        printService.consoleLog("message", "Wizzard can control orks so they are not able to damage them.");
        Card a = new Card("ID1", "WaterWizzard", 10);
        Card b = new Card("ID2", "WaterOrk", 100);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    //
    @Test
    void fightKnightVsWaterSpell() {
        printService.consoleLog("unitTest", "BattleLogicService: fight knight vs water spell");
        printService.consoleLog("message", "The armor of Knights is so heavy that water spells make them drown them instantly.");
        Card a = new Card("ID1", "WaterKnight", 100);
        Card b = new Card("ID2", "WaterSpell", 10);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(b, battleLogicService.fightTwoCards(a, b));
    }

    @Test
    void fightKrakenVsSpells() {
        printService.consoleLog("unitTest", "BattleLogicService: fight kranken vs spell");
        printService.consoleLog("message", "The Kraken is immune against spells.");
        Card a = new Card("ID1", "WaterKraken", 10);
        Card b = new Card("ID2", "WaterSpell", 20);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    @Test
    void fightFireElveVsDragon() {
        printService.consoleLog("unitTest", "BattleLogicService: fight fire elve vs dragon");
        printService.consoleLog("message", "The fire elves know dragons since they were little and can evade their attacks.");
        Card a = new Card("ID1", "FireElve", 10);
        Card b = new Card("ID2", "Dragon", 100);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(a, battleLogicService.fightTwoCards(a, b));
    }

    @Test
    void fightTwoSpecificCards() {
        printService.consoleLog("unitTest", "BattleLogicService: fight two specific cards");
        Card a = new Card("ID1", "WaterDragon", 20.0);
        Card b = new Card("ID2", "FireDragon", 30.0);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
    }

    //
    @Test
    void plotFight() {
        printService.consoleLog("unitTest", "BattleLogicService: plot fight");
        Card a = new Card("ID1", "WaterKraken", 20);
        Card b = new Card("ID2", "WaterKraken", 20);
        printService.printCard(a);
        printService.printCard(b);
        printService.printWinningCard(battleLogicService.fightTwoCards(a, b));
        Assertions.assertEquals(cardService.getPlotCard().getType(), battleLogicService.fightTwoCards(a, b).getType());
    }

}