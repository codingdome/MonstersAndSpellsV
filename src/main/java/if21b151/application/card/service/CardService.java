package if21b151.application.card.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.user.model.User;

import java.util.List;

public interface CardService {

    public Card getPlotCard();

    public CardPackage cardsToCardPackage(Card[] cards);

    public CardPackage addNewPackage(CardPackage cardPackage);

    public int acquirePackage(User user);

    public List<Card> getAllUserCards(User user);

    public List<Card> getUserDeck(User user);

    public int configureDeck(User user, List<String> cardIDs);

    public void updateCardOwner(Card card, User user);

    public void deleteCardFromDeck(Card card);

}
