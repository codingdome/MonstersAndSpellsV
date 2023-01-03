package if21b151.application.gameengine.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.gameengine.battlelogic.BattleLogicService;
import if21b151.application.gameengine.battlelogic.BattleLogicServiceImpl;
import if21b151.application.gameengine.repository.GameEngineRepository;
import if21b151.application.gameengine.repository.GameEngineRepositoryImpl;
import if21b151.application.user.model.User;

import java.util.List;
import java.util.Objects;

public class GameEngineServiceImpl implements GameEngineService {
    GameEngineRepository gameEngineRepository = new GameEngineRepositoryImpl();
    BattleLogicService battleLogicService = new BattleLogicServiceImpl();
    CardService cardService = new CardServiceImpl();

    @Override
    public int registerForBattle(User user) {
        if (gameEngineRepository.checkIfUserIsWaiting()) {
            //start battle
            User user2 = new User();
            user2.setUsername(gameEngineRepository.getLatestUsernameWaiting());

            battleTwoPlayer(user, user2);

            return 0;
        } else {
            //add user to waiting list
            gameEngineRepository.addUserToWaitingList(user);
            return 1;
        }
    }

    private void battleTwoPlayer(User a, User b) {
        int cardBattleWinsA = 0;
        int cardBattleWinsB = 0;
        List<Card> cardsPlayerA = cardService.getUserDeck(a);
        List<Card> cardsPlayerB = cardService.getUserDeck(b);
        for (int i = 0; i < cardsPlayerA.size(); i++) {
            Card winningCard = battleLogicService.fightTwoCards(cardsPlayerA.get(i), cardsPlayerB.get(i));

            if (!Objects.equals(winningCard.getId(), "0")) {
                if (Objects.equals(cardsPlayerA.get(i).getUsername(), winningCard.getUsername())) {
                    //A hat won
                    cardBattleWinsA++;
                    cardService.updateCardOwner(cardsPlayerB.get(i), a);
                } else {
                    //B hat won
                    cardBattleWinsB++;
                    cardService.updateCardOwner(cardsPlayerA.get(i), b);
                }
            }
            cardService.deleteCardFromDeck(cardsPlayerA.get(i));
            cardService.deleteCardFromDeck(cardsPlayerB.get(i));

            if(cardBattleWinsA > cardBattleWinsB) {
                //user a gewinnt
                //update elo
                //update won
            } else {
                //user b gewinnt
                //update elo
                //update won
            }
        }
    }
}
