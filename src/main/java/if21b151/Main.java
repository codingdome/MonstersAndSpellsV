package if21b151;

import if21b151.httpserver.server.Server;
import if21b151.httpserver.service.users.UserService;
import if21b151.httpserver.utils.Router;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

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

        return router;
    }
}
