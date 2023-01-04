package if21b151.application.gameengine.model;

import if21b151.application.card.model.Card;
import if21b151.application.user.model.User;

import java.util.List;

public class BattleLog {
    User playerOne;
    User playerTwo;
    List<Card> deckPlayerOne;
    List<Card> deckPlayerTwo;
    List<Card> winningCards;
    User winner;
    List<String> battleLog;

    public List<Card> getWinningCards() {
        return winningCards;
    }

    public void setWinningCards(List<Card> winningCards) {
        this.winningCards = winningCards;
    }

    public User getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(User playerOne) {
        this.playerOne = playerOne;
    }

    public User getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(User playerTwo) {
        this.playerTwo = playerTwo;
    }

    public List<Card> getDeckPlayerOne() {
        return deckPlayerOne;
    }

    public void setDeckPlayerOne(List<Card> deckPlayerOne) {
        this.deckPlayerOne = deckPlayerOne;
    }

    public List<Card> getDeckPlayerTwo() {
        return deckPlayerTwo;
    }

    public void setDeckPlayerTwo(List<Card> deckPlayerTwo) {
        this.deckPlayerTwo = deckPlayerTwo;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public List<String> getBattleLog() {
        return battleLog;
    }

    public void setBattleLog(List<String> battleLog) {
        this.battleLog = battleLog;
    }
}
