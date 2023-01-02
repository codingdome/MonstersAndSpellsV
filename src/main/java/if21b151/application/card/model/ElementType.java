package if21b151.application.card.model;

public enum ElementType {
    WATER("Water"),
    FIRE("Fire"),
    REGULAR("Regular");
    private String name;

    ElementType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
