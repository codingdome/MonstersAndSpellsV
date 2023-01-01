package if21b151.application.user.model;

public class Stats {
    String username;
    String name;
    int elo;
    int coins;
    int won;
    int lost;

    public Stats(String username, String name, int elo, int coins, int won, int lost) {
        this.username = username;
        this.name = name;
        this.elo = elo;
        this.coins = coins;
        this.won = won;
        this.lost = lost;
    }

    public Stats() {
        this.username = "";
        this.name = "";
        this.elo = 1000;
        this.coins = 20;
        this.won = 0;
        this.lost = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }
}
