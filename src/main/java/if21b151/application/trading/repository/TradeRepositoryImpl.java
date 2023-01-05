package if21b151.application.trading.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradeRepositoryImpl implements TradeRepository {

    CardService cardService = new CardServiceImpl();

    @Override
    public List<Trade> getTradesFromDB() {
        List<Trade> trades = new ArrayList<>();

        String sql = """
                select * from trades
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Trade trade = new Trade();
                trade.setUsername(results.getString(1));
                trade.setId(results.getString(2));
                trade.setCardToTrade(results.getString(3));
                trade.setType(results.getString(4));
                trade.setMinimumDamage(results.getDouble(5));
                trades.add(trade);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trades;
    }

    @Override
    public void addTradeToDB(User user, Trade trade) {
        String sql = """
                insert into trades (username,id,card_to_trade,type,minimum_damage) values(?,?,?,?,?)
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, trade.getId());
            statement.setString(3, trade.getCardToTrade());
            statement.setString(4, trade.getType());
            statement.setDouble(5, trade.getMinimumDamage());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteTradeFromDB(User user, String tradeID) {
        int count = 0;
        String countSql = """
                select count(*) from trades where username=? and id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(countSql);
            statement.setString(1, user.getUsername());
            statement.setString(2, tradeID);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (count < 1) {
            return 1;
        }

        String sql = """
                delete from trades where username=? and id =?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, tradeID);
            statement.execute();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int tradeDB(User user, String tradeID, String cardID) {

        //1 get trade by ID
        Trade trade = getTradeByID(tradeID);
        //2 if trade=null -> return 1
        if (trade == null) {
            return 1;
        }
        //3 if trade-username == user.getUsername() -> return 2
        if (Objects.equals(trade.getUsername(), user.getUsername())) {
            return 2;
        }
        //Die Angebotskarte
        Card card = cardService.getCardByID(cardID);
        //4 trade card to low / wrong type -> return 3
        if (card.getDamage() < trade.getMinimumDamage() || !Objects.equals(card.getType(), trade.getType())) {
            return 3;
        }

        Card tradeCard = cardService.getCardByID(trade.getCardToTrade());

        User tradeUser = new User();
        tradeUser.setUsername(trade.getUsername());

        cardService.updateCardOwner(card, tradeUser);
        cardService.updateCardOwner(tradeCard, user);
        deleteTradeFromDB(tradeUser, tradeID);
        return 0;
    }

    public Trade getTradeByID(String tradeID) {
        String sql = """
                select * from trades where id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, tradeID);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Trade trade = new Trade();
                trade.setUsername(results.getString(1));
                trade.setId(results.getString(2));
                trade.setCardToTrade(results.getString(3));
                trade.setType(results.getString(4));
                trade.setMinimumDamage(results.getDouble(5));
                return trade;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
