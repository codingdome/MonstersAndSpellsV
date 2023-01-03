package if21b151.application.card.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.model.ElementType;
import if21b151.application.card.model.MonsterType;
import if21b151.application.card.repository.CardRepository;
import if21b151.application.card.repository.CardRepositoryImpl;
import if21b151.application.user.model.User;

import java.util.List;

public class CardServiceImpl implements CardService {

    CardRepository cardRepository = new CardRepositoryImpl();

    @Override
    public CardPackage cardsToCardPackage(Card[] cards) {
        CardPackage cardPackage = new CardPackage();
        for (Card card : cards) {
            cardPackage.addToPackage(card);
        }
        return cardPackage;
    }

    @Override
    public CardPackage addNewPackage(CardPackage cardPackage) {
        cardRepository.addNewPackage(cardPackage);
        return cardPackage;
    }

    @Override
    public int acquirePackage(User user) {
        return cardRepository.acquirePackage(user);
    }

    @Override
    public List<Card> getAllUserCards(User user) {
        return cardRepository.getAllCardsByUsername(user.getUsername());
    }

    @Override
    public List<Card> getUserDeck(User user) {
        return cardRepository.getUserDeckCards(user.getUsername());
    }

    @Override
    public int configureDeck(User user, List<String> cardIDs) {
        return cardRepository.configureDeck(user, cardIDs);
    }

    @Override
    public void updateCardOwner(Card card, User user) {
        cardRepository.updateCardOwner(card, user);
    }

    @Override
    public void deleteCardFromDeck(Card card) {
        cardRepository.deleteCardFromDeck(card);
    }

    @Override
    public Card getPlotCard() {
        return new Card("0", "0", "0", "0", 0, ElementType.WATER, MonsterType.ELVE, 0, 0);
    }

}
