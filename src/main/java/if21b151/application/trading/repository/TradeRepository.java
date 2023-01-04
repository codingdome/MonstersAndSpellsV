package if21b151.application.trading.repository;

import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;

import java.util.List;

public interface TradeRepository {
    List<Trade> getTradesFromDB();

    void addTradeToDB(User user, Trade trade);
}
