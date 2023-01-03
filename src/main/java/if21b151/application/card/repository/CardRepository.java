package if21b151.application.card.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.user.model.User;

import java.util.List;

public interface CardRepository {

    int countPackages();

    void addNewPackage(CardPackage cardPackage);

    int acquirePackage(User user);

    List<Card> getAllCardsByUsername(String username);

    List<Card> getUserDeckCards(String username);

    int configureDeck(User user, List<String> cardIDs);

    void updateCardOwner(Card card, User user);

    void deleteCardFromDeck(Card card);
}

