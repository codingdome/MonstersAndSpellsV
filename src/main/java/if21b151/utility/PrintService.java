package if21b151.utility;

import if21b151.application.card.model.Card;
import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;

public interface PrintService {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_MAGENTA = "\u001B[95m";

    public void consoleLog(String position, String message);

    public void printUser(User user);

    public void printCard(Card card);

    public void printWinningCard(Card card);

    public void printTrade(Trade trade);
}
