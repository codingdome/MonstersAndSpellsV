package if21b151.application.trading.repository;

import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeRepositoryImpl implements TradeRepository {

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
}
