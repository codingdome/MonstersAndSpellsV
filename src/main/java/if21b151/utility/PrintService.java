package if21b151.utility;

import if21b151.application.card.model.Card;
import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;

public interface PrintService {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_MAGENTA = "\u001B[95m";
    static final String ANSI_RED = "\u001B[31m";

    void consoleLog(String position, String message);

    void printUser(User user);

    void printCard(Card card);

    void printWinningCard(Card card);

    void printTrade(Trade trade);

    void printRequest(Request request);

    void printResponse(Response response);
}
