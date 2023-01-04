package if21b151.application.trading.service;

import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;

import java.util.List;

public interface TradeService {
    List<Trade> getTrades();

    void addTrade(User user, Trade trade);
}
