package if21b151.application.card.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.model.ElementType;
import if21b151.application.card.model.MonsterType;
import if21b151.application.card.repository.CardRepository;
import if21b151.application.card.repository.CardRepositoryImpl;

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
    public Card getPlotCard() {
        return new Card("0", "0", "0", "0", 0, ElementType.WATER, MonsterType.ELVE, 0, 0);
    }

}
