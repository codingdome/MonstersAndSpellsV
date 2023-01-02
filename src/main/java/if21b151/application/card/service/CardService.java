package if21b151.application.card.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;

public interface CardService {

    public Card getPlotCard();

    public CardPackage cardsToCardPackage(Card[] cards);

    public CardPackage addNewPackage(CardPackage cardPackage);

}
