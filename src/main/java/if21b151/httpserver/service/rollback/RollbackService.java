package if21b151.httpserver.service.rollback;

import if21b151.database.DataBase;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class RollbackService implements Service {
    PrintService printService = new PrintServiceImpl();

    String users = """
            delete from users;
            """;
    String battles = """
            delete from battles;
            """;
    String stats = """
            delete from stats;
            """;
    String packages = """
            delete from packages;
            """;
    String cards = """
            delete from cards;
            """;
    String trades = """
            delete from trades;
            """;

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && Objects.equals(request.getHeaderMap().getHeader("Authorization"), "Basic admin-mtcgToken")) {
            printService.consoleLog("server", "delete DB");
            try {
                PreparedStatement statement = DataBase.getConnection().prepareStatement(users);
                PreparedStatement statement2 = DataBase.getConnection().prepareStatement(battles);
                PreparedStatement statement3 = DataBase.getConnection().prepareStatement(stats);
                PreparedStatement statement4 = DataBase.getConnection().prepareStatement(packages);
                PreparedStatement statement5 = DataBase.getConnection().prepareStatement(cards);
                PreparedStatement statement6 = DataBase.getConnection().prepareStatement(trades);

                statement.execute();
                statement2.execute();
                statement3.execute();
                statement4.execute();
                statement5.execute();
                statement6.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return new Response(HttpStatus.OK, ContentType.JSON, "delete all -> done.");
    }
}
