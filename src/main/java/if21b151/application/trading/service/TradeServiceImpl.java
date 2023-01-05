package if21b151.application.trading.service;

import if21b151.application.trading.model.Trade;
import if21b151.application.trading.repository.TradeRepository;
import if21b151.application.trading.repository.TradeRepositoryImpl;
import if21b151.application.user.model.User;

import java.util.List;

public class TradeServiceImpl implements TradeService {
    TradeRepository tradeRepository = new TradeRepositoryImpl();

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.getTradesFromDB();
    }

    @Override
    public void addTrade(User user, Trade trade) {
        tradeRepository.addTradeToDB(user, trade);
    }

    @Override
    public int deleteTrade(User user, String tradeID) {
        return tradeRepository.deleteTradeFromDB(user, tradeID);
    }

    @Override
    public int trade(User user, String tradeID, String cardID) {
        return tradeRepository.tradeDB(user, tradeID, cardID);
    }
}
