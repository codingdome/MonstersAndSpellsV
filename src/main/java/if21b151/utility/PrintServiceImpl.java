package if21b151.utility;

import if21b151.application.card.model.Card;
import if21b151.application.trading.model.Trade;
import if21b151.application.user.model.User;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintServiceImpl implements PrintService {
    @Override
    public void consoleLog(String position, String message) {
        System.out.println();
        switch (position) {
            case "unitTest":
                System.out.println(ANSI_CYAN + position.toUpperCase() + ANSI_RESET + " " + message);
                break;

            case "server":
                System.out.println(ANSI_MAGENTA + position.toUpperCase() + ANSI_RESET + " operation: " + message);
                break;

            case "message":
                System.out.println(ANSI_GREEN + position.toUpperCase() + ANSI_RESET + " " + message);
            default:
                break;
        }
        System.out.println();
    }

    @Override
    public void printUser(User user) {
        System.out.println();
        System.out.println(ANSI_MAGENTA + "PRINTUSER: " + user.getUsername().toUpperCase() + ANSI_RESET);
        if (user == null) {
            return;
        }
        final Object[][] table = new String[2][];
        table[0] = new String[]{"username", "password", "name", "bio", "img", "token", "elo", "coins", "won", "lost"};
        table[1] = new String[]{user.getUsername(), user.getPassword(), user.getName(), user.getBio(), user.getImg(), user.getToken(), Integer.toString(user.getStats().getElo()), Integer.toString(user.getStats().getCoins()), Integer.toString(user.getStats().getWon()), Integer.toString(user.getStats().getLost())};
        for (final Object[] row : table) {
            System.out.format("%-20s%-20s%-20s%-20s%-10s%-30s%-10s%-10s%-10s%-10s%n", row);
        }
        System.out.println();
    }

    @Override
    public void printCard(Card card) {
        System.out.println();
        System.out.println(ANSI_MAGENTA + "PRINTCARD: " + card.getName().toUpperCase() + ANSI_RESET);
        if (card == null) {
            return;
        }
        final Object[][] table = new String[2][];
        table[0] = new String[]{"id", "name", "username", "type", "damage", "elementType", "monsterType", "deck", "trade"};

        String monsterType;
        if (card.getType().equals("spell")) {
            monsterType = "-";
        } else {
            monsterType = card.getMonsterType().toString();
        }

        table[1] = new String[]{card.getId(), card.getName(), card.getUsername(), card.getType(), Double.toString(card.getDamage()), card.getElementType().toString(), card.getMonsterType().toString(), Integer.toString(card.getDeck()), Integer.toString(card.getTrade())};


        for (final Object[] row : table) {
            System.out.format("%-40s%-15s%-15s%-15s%-15s%-15s%-15s%-10s%-10s%n", row);
        }
        System.out.println();
    }

    @Override
    public void printWinningCard(Card card) {
        System.out.println();
        System.out.println(ANSI_BLUE + "WINNINGCARD: " + card.getName().toUpperCase() + ANSI_RESET);
        if (card == null) {
            return;
        }
        final Object[][] table = new String[2][];
        table[0] = new String[]{"id", "name", "username", "type", "damage", "elementType", "monsterType", "deck", "trade"};

        String monsterType;
        if (card.getType().equals("spell")) {
            monsterType = "-";
        } else {
            monsterType = card.getMonsterType().toString();
        }

        table[1] = new String[]{card.getId(), card.getName(), card.getUsername(), card.getType(), Double.toString(card.getDamage()), card.getElementType().toString(), card.getMonsterType().toString(), Integer.toString(card.getDeck()), Integer.toString(card.getTrade())};


        for (final Object[] row : table) {
            System.out.format("%-40s%-15s%-15s%-15s%-15s%-15s%-15s%-10s%-10s%n", row);
        }
        System.out.println();
    }

    @Override
    public void printTrade(Trade trade) {
        System.out.println();
        System.out.println(ANSI_MAGENTA + "PRINTTRADE: " + trade.getId().toUpperCase() + ANSI_RESET);
        if (trade == null) {
            return;
        }
        final Object[][] table = new String[2][];
        table[0] = new String[]{"username", "id", "card to trade", "type", "min damage"};

        table[1] = new String[]{trade.getUsername(), trade.getId(), trade.getCardToTrade(), trade.getType(), Double.toString(trade.getMinimumDamage())};


        for (final Object[] row : table) {
            System.out.format("%-20s%-50s%-50s%-15s%-10s%n", row);
        }
        System.out.println();
    }

    @Override
    public void printRequest(Request request) {
        if (request.getMethod() == null) {
            return;
        }
        String title = "| " + request.getMethod().toString().toUpperCase() + " " + request.getUrlContent() + " " + request.getHeaderMap().getHeader("Authorization") + " " + request.getHeaderMap().getHeader("Content-Type");
        int titleLength = title.length();
        String body = "| data: " + request.getBody();
        int bodyLength = body.length();
        int finalLength = titleLength;
        if (bodyLength > finalLength) {
            finalLength = bodyLength;
        }
        String lines = " ";
        for (int i = 0; i < finalLength; i++) {
            lines = lines + "-";
        }
        System.out.println();
        System.out.println(ANSI_RED + " -------------");
        System.out.println("|   REQUEST   |" + ANSI_RESET);
        System.out.println(lines);
        System.out.println(title);
        System.out.println("| BODY: " + request.getBody());
        System.out.println(lines);
        System.out.println();
    }

    @Override
    public void printResponse(Response response) {
        if (response == null) {
            return;
        }
        System.out.println();
        System.out.println(ANSI_RED + " ---------------------");
        System.out.println("| RESPONSE   " + ANSI_RESET);
        if (response.getStatus() == 400 || response.getStatus() == 401 || response.getStatus() == 500) {
            System.out.println(ANSI_RED + "| " + response.getStatus() + " " + response.getMessage() + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "| " + response.getStatus() + " " + response.getMessage() + ANSI_RESET);
        }
        System.out.println("| Content-Type: " + response.getContentType());
        System.out.println("| CONTENT: " + response.getContent());
        System.out.println(" ---------------------");
        System.out.println();
    }
}
