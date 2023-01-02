package if21b151.application.card.model;

public enum MonsterType {
    GOBLIN("Goblin"),
    DRAGON("Dragon"),
    WIZZARD("Wizzard"),
    KNIGHT("Knight"),
    KRAKEN("Kraken"),
    TROLL("Troll"),
    ORK("Ork"),
    ELVE("Elve"),
    SPELL("Spell");

    private String name;

    MonsterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
