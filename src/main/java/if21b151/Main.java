package if21b151;

import if21b151.httpserver.server.Server;
import if21b151.httpserver.service.cards.CardService;
import if21b151.httpserver.service.sessions.SessionService;
import if21b151.httpserver.service.stats.StatsService;
import if21b151.httpserver.service.users.UserService;
import if21b151.httpserver.utils.Router;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(12345, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter() {
        Router router = new Router();
        router.addService("/users", new UserService());
        router.addService("/sessions", new SessionService());
        router.addService("/users/kienboec", new UserService());
        router.addService("/users/altenhof", new UserService());
        router.addService("/stats", new StatsService());
        router.addService("/packages", new CardService());
        router.addService("/transactions", new CardService());
        router.addService("/cards", new CardService());
        router.addService("/deck", new CardService());
        return router;
    }
}
