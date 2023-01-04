package if21b151.application.gameengine.service;

import if21b151.application.card.model.Card;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.gameengine.battlelogic.BattleLogicService;
import if21b151.application.gameengine.battlelogic.BattleLogicServiceImpl;
import if21b151.application.gameengine.model.BattleLog;
import if21b151.application.gameengine.repository.GameEngineRepository;
import if21b151.application.gameengine.repository.GameEngineRepositoryImpl;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.application.user.service.UserServiceImpl;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameEngineServiceImpl implements GameEngineService {
    GameEngineRepository gameEngineRepository = new GameEngineRepositoryImpl();
    BattleLogicService battleLogicService = new BattleLogicServiceImpl();
    CardService cardService = new CardServiceImpl();
    UserService userService = new UserServiceImpl();
    PrintService printService = new PrintServiceImpl();

    @Override
    public BattleLog registerForBattle(User user) {
        if (gameEngineRepository.checkIfUserIsWaiting()) {
            //start battle
            User user2 = gameEngineRepository.getLatestUserWaiting();

            printService.consoleLog("server", "Start battle between:");
            printService.printUser(user);
            printService.printUser(user2);

            return battleTwoPlayer(user, user2);
        } else {
            BattleLog battleLog = new BattleLog();
            battleLog.setWinner(null);
            printService.consoleLog("server", "New user added to lobby: ");
            printService.printUser(user);
            gameEngineRepository.addUserToWaitingList(user);
            return battleLog;
        }
    }

    private BattleLog battleTwoPlayer(User a, User b) {
        BattleLog battleLog = new BattleLog();
        battleLog.setPlayerOne(a);
        battleLog.setPlayerTwo(b);
        List<Card> cardsPlayerA = cardService.getUserDeck(a);
        List<Card> cardsPlayerB = cardService.getUserDeck(b);
        battleLog.setDeckPlayerOne(cardService.getUserDeck(a));
        battleLog.setDeckPlayerTwo(cardService.getUserDeck(b));

        int cardBattleWinsA = 0;
        int cardBattleWinsB = 0;

        List<Card> winningCards = new ArrayList<>();
        List<String> battleLogString = new ArrayList<>();

        battleLogString.add("START");

        for (int i = 0; i < cardsPlayerA.size(); i++) {
            battleLogString.add("ROUND-" + (i + 1));
            battleLogString.add("CARD1: " + cardsPlayerA.get(i).getName() + " CARD2: " + cardsPlayerB.get(i).getName());
            Card winningCard = battleLogicService.fightTwoCards(cardsPlayerA.get(i), cardsPlayerB.get(i));
            winningCards.add(winningCard);

            if (!Objects.equals(winningCard.getId(), "0")) {
                battleLogString.add("WINNING CARD-" + winningCard.getName() + " " + winningCard.getId());

                if (Objects.equals(cardsPlayerA.get(i).getUsername(), winningCard.getUsername())) {
                    //A hat won
                    cardBattleWinsA++;
                    battleLogString.add("ROUND-WINNER: " + a.getUsername());
                    cardService.updateCardOwner(cardsPlayerB.get(i), a);
                } else {
                    //B hat won
                    cardBattleWinsB++;
                    battleLogString.add("ROUND-WINNER: " + b.getUsername());
                    cardService.updateCardOwner(cardsPlayerA.get(i), b);
                }
            } else {
                battleLogString.add("PLOT NO WINNING CARD");
            }
            cardService.deleteCardFromDeck(cardsPlayerA.get(i));
            cardService.deleteCardFromDeck(cardsPlayerB.get(i));
        }
        battleLog.setWinningCards(winningCards);

        if (cardBattleWinsA > cardBattleWinsB) {
            //user a won
            //update elo
            //update won
            updateStats(a, b, battleLog);

        } else if (cardBattleWinsA == cardBattleWinsB) {
            battleLogString.add("NO WINNER");
            User user = new User();
            battleLog.setWinner(user);
        } else {
            //user b won
            //update elo
            //update won
            updateStats(b, a, battleLog);
        }
        userService.update(a);
        userService.update(b);

        battleLog.setBattleLog(battleLogString);

        return battleLog;
    }

    private void updateStats(User a, User b, BattleLog battleLog) {
        a.getStats().setWon(a.getStats().getWon() + 1);
        a.getStats().setElo(a.getStats().getElo() + 30);
        b.getStats().setLost(b.getStats().getLost() + 1);
        b.getStats().setElo(b.getStats().getElo() - 50);
        battleLog.setWinner(a);
    }
}
