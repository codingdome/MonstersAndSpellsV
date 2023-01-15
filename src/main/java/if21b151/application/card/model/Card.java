package if21b151.application.card.model;

import java.util.Objects;
import java.util.Random;

public class Card {
    protected String id;
    protected String name;
    protected String username;
    protected String type;
    protected double damage;
    protected ElementType elementType;
    protected MonsterType monsterType;
    protected int deck;
    protected int trade;

    public Card(String id, String name, String username, String type, double damage, ElementType elementType, MonsterType monsterType, int deck, int trade) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.type = type;
        this.damage = damage;
        this.elementType = elementType;
        this.monsterType = monsterType;
        this.deck = deck;
        this.trade = trade;
    }

    public Card(String id, String name, String username, double damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.username = username;
        String[] nameToArray = this.name.split("(?=\\p{Lu})");

        if (nameToArray.length == 1) {
            this.type = "monster";
            this.monsterType = stringToMonsterType(nameToArray[0]);
            this.elementType = getRandomElementType();
        } else {
            this.elementType = stringToElementType(nameToArray[0]);
            if (Objects.equals(nameToArray[1], "Spell")) {
                type = "spell";
                monsterType = MonsterType.SPELL;
            } else {
                type = "monster";
                monsterType = stringToMonsterType(nameToArray[1]);
            }
        }
    }

    public Card(String id, String name, double damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.username = "unallocated";
        String[] nameToArray = this.name.split("(?=\\p{Lu})");

        if (nameToArray.length == 1) {
            this.type = "monster";
            this.monsterType = stringToMonsterType(nameToArray[0]);
            this.elementType = getRandomElementType();
        } else {
            this.elementType = stringToElementType(nameToArray[0]);
            if (Objects.equals(nameToArray[1], "Spell")) {
                type = "spell";
                monsterType = MonsterType.SPELL;
            } else {
                type = "monster";
                monsterType = stringToMonsterType(nameToArray[1]);
            }
        }
    }

    public Card() {

        this.id = "";
        this.name = "";
        this.username = "unallocated";
        this.damage = 0;

        String[] nameToArray = this.name.split("(?=\\p{Lu})");

        if (nameToArray.length == 1) {
            this.type = "monster";
            this.monsterType = stringToMonsterType(nameToArray[0]);
            this.elementType = getRandomElementType();
        } else {
            this.elementType = stringToElementType(nameToArray[0]);
            if (Objects.equals(nameToArray[1], "Spell")) {
                type = "spell";
                monsterType = MonsterType.SPELL;
            } else {
                type = "monster";
                monsterType = stringToMonsterType(nameToArray[1]);
            }
        }
    }

    private MonsterType stringToMonsterType(String monsterTypeString) {
        return switch (monsterTypeString) {
            case "Goblin" -> MonsterType.GOBLIN;
            case "Dragon" -> MonsterType.DRAGON;
            case "Wizzard" -> MonsterType.WIZZARD;
            case "Knight" -> MonsterType.KNIGHT;
            case "Kraken" -> MonsterType.KRAKEN;
            case "Troll" -> MonsterType.TROLL;
            case "Ork" -> MonsterType.ORK;
            case "Elve" -> MonsterType.ELVE;
            default -> MonsterType.SPELL;
        };
    }

    private ElementType stringToElementType(String elementTypeString) {
        return switch (elementTypeString) {
            case "Water" -> ElementType.WATER;
            case "Fire" -> ElementType.FIRE;
            case "Regular" -> ElementType.REGULAR;
            default -> null;
        };
    }

    private ElementType getRandomElementType() {
        Random r = new Random();
        int randomElementInt = r.nextInt(3);

        return switch (randomElementInt) {
            case 0 -> ElementType.FIRE;
            case 1 -> ElementType.REGULAR;
            default -> ElementType.WATER;
        };
    }

    /*getter/setter*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDamage() {
        return damage;
    }

//    public void setDamage(double damage) {
//        this.damage = damage;
//    }

    public ElementType getElementType() {
        return elementType;
    }

//    public void setElementType(ElementType elementType) {
//        this.elementType = elementType;
//    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

//    public void setMonsterType(MonsterType monsterType) {
//        this.monsterType = monsterType;
//    }

    public int getDeck() {
        return deck;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }

    public int getTrade() {
        return trade;
    }

    public void setTrade(int trade) {
        this.trade = trade;
    }
}

