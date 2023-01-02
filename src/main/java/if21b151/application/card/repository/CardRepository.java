package if21b151.application.card.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.user.model.User;

import java.util.List;

public interface CardRepository {

    public int countPackages();

    public void addNewPackage(CardPackage cardPackage);

    public int acquirePackage(User user);

    public List<Card> getAllCardsByUsername(String username);

    public List<Card> getUserDeckCards(String username);

    public int configureDeck(User user, List<String> cardIDs);
}

