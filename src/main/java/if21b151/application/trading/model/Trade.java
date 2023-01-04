package if21b151.application.trading.model;

public class Trade {
    String username;
    String id;
    String cardToTrade;
    String type;
    Double minimumDamage;

    public Trade(String username, String id, String cardToTrade, String type, Double minimumDamage) {
        this.username = username;
        this.id = id;
        this.cardToTrade = cardToTrade;
        this.type = type;
        this.minimumDamage = minimumDamage;
    }

    public Trade() {
        this.username = "";
        this.id = "";
        this.cardToTrade = "";
        this.type = "";
        this.minimumDamage = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardToTrade() {
        return cardToTrade;
    }

    public void setCardToTrade(String cardToTrade) {
        this.cardToTrade = cardToTrade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(Double minimumDamage) {
        this.minimumDamage = minimumDamage;
    }
}