package if21b151.application.gameengine.battlelogic;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.ElementType;
import if21b151.application.card.model.MonsterType;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.util.Objects;

public class BattleLogicServiceImpl implements BattleLogicService {
    CardService cardService = new CardServiceImpl();
    PrintService printService = new PrintServiceImpl();

    @Override
    public Card fightTwoCards(Card a, Card b) {
        Card winnerCard = initFight(a, b);
        if (winnerCard != null) {
            printService.consoleLog("server", "Winning Card:");
            printService.printCard(winnerCard);
            return winnerCard;
        } else {
            printService.consoleLog("server", "Fight Cards: Plot!");
            return cardService.getPlotCard();
        }
    }

    private Card initFight(Card a, Card b) {
        if ((Objects.equals(a.getType(), "monster")) && (Objects.equals(a.getType(), b.getType()))) {
            return monsterFight(a, b);
        } else if (!Objects.equals(a.getType(), b.getType())) {
            return mixedFight(a, b);
        } else {
            return spellFight(a, b);
        }
    }

    //Function pure Monster Fight, returns winning Monster
    private Card monsterFight(Card a, Card b) {
        /*check specialties*/
        if ((a.getMonsterType() == MonsterType.GOBLIN) && (b.getMonsterType() == MonsterType.DRAGON)) {
            return b;
        } else if ((a.getMonsterType() == MonsterType.DRAGON) && (b.getMonsterType() == MonsterType.GOBLIN)) {
            return a;
        } else if ((a.getMonsterType() == MonsterType.WIZZARD) && (b.getMonsterType() == MonsterType.ORK)) {
            return a;
        } else if ((a.getMonsterType() == MonsterType.ORK) && (b.getMonsterType() == MonsterType.WIZZARD)) {
            return b;
        } else if (((a.getMonsterType() == MonsterType.ELVE) && a.getElementType() == ElementType.FIRE) && (b.getMonsterType() == MonsterType.DRAGON)) {
            return a;
        } else if (((b.getMonsterType() == MonsterType.ELVE) && b.getElementType() == ElementType.FIRE) && (a.getMonsterType() == MonsterType.DRAGON)) {
            return b;
        }

        return equalDamage(a, b);
    }

    //mixed fight
    private Card mixedFight(Card a, Card b) {
        /*check specialties*/

        //check for KRAKEN in mixed Fights:
        if (a.getMonsterType() != null && a.getMonsterType() == MonsterType.KRAKEN) {
            return a;
        } else if (b.getMonsterType() != null && b.getMonsterType() == MonsterType.KRAKEN) {
            return b;
        } else if ((Objects.equals(a.getType(), "spell") && a.getElementType() == ElementType.WATER) && (b.getMonsterType() == MonsterType.KNIGHT)) {
            return a;
        } else if ((Objects.equals(b.getType(), "spell") && b.getElementType() == ElementType.WATER) && (a.getMonsterType() == MonsterType.KNIGHT)) {
            return b;
        } else {
            //compare by elements:
            return compareElement(a, b);
        }
    }

    //pure Spell Fight
    private Card spellFight(Card a, Card b) {
        return compareElement(a, b);
    }


    private Card compareElement(Card a, Card b) {
        if (a.getElementType() == b.getElementType()) {
            return equalDamage(a, b);
        }
        if (a.getElementType() == ElementType.WATER.WATER && b.getElementType() == ElementType.WATER.FIRE) {
            return calculatedDamage(a, b);
        } else if (a.getElementType() == ElementType.WATER.FIRE && b.getElementType() == ElementType.WATER.WATER) {
            return calculatedDamage(b, a);
        } else if (a.getElementType() == ElementType.WATER.FIRE && b.getElementType() == ElementType.WATER.REGULAR) {
            return calculatedDamage(a, b);
        } else if (a.getElementType() == ElementType.WATER.REGULAR && b.getElementType() == ElementType.WATER.FIRE) {
            return calculatedDamage(b, a);
        } else if (a.getElementType() == ElementType.WATER.REGULAR && b.getElementType() == ElementType.WATER.WATER) {
            return calculatedDamage(a, b);
        } else {
            return calculatedDamage(b, a);
        }
    }

    private Card equalDamage(Card a, Card b) {
        if (a.getDamage() > b.getDamage()) {
            return a;
        } else if (b.getDamage() > a.getDamage()) {
            return b;
        } else {
            return null;
        }
    }

    private Card calculatedDamage(Card strong, Card weak) {
        if ((strong.getDamage() * 2) > (weak.getDamage() / 2)) {
            return strong;
        } else if ((strong.getDamage() * 2) == (weak.getDamage() / 2)) {
            return null;
        } else {
            return weak;
        }
    }
}
