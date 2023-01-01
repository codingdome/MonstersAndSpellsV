package if21b151.utility;

import if21b151.application.user.model.User;

public class PrintServiceImpl implements PrintService {
    @Override
    public void consoleLog(String position, String message) {
        switch (position) {
            case "unitTest":
                System.out.println(ANSI_CYAN + position.toUpperCase() + ANSI_RESET + " " + message);
                break;

            case "server":
                System.out.println(ANSI_MAGENTA + position.toUpperCase() + ANSI_RESET + " operation: " + message);
            default:
                break;
        }
    }

    @Override
    public void printUser(User user) {
        System.out.println(ANSI_MAGENTA + "PRINTUSER " + user.getUsername().toUpperCase() + ANSI_RESET);
        if (user == null) {
            return;
        }
        final Object[][] table = new String[2][];
        table[0] = new String[]{"username", "password", "name", "bio", "img", "token", "elo", "coins", "won", "lost"};
        table[1] = new String[]{user.getUsername(), user.getPassword(), user.getName(), user.getBio(), user.getImg(), user.getToken(), Integer.toString(user.getStats().getElo()), Integer.toString(user.getStats().getCoins()), Integer.toString(user.getStats().getWon()), Integer.toString(user.getStats().getLost())};
        for (final Object[] row : table) {
            System.out.format("%-20s%-20s%-20s%-20s%-10s%-30s%-10s%-10s%-10s%-10s%n", row);
        }
    }
}
