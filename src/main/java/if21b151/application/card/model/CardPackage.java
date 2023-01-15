package if21b151.application.card.model;

import java.util.ArrayList;
import java.util.List;

public class CardPackage {
    public List<Card> packageCards = new ArrayList<>(5);

    public void addToPackage(Card card) {
        this.packageCards.add(card);
    }

    public List<Card> getPackageCards() {
        return packageCards;
    }
}
