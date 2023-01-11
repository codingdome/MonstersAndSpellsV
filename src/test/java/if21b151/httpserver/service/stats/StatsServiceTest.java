package if21b151.httpserver.service.stats;

import if21b151.database.DataBase;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.service.sessions.SessionService;
import if21b151.httpserver.service.users.UserService;
import if21b151.httpserver.utils.RequestBuilder;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StatsServiceTest {

    PrintService printService;
    UserService userService;
    RequestBuilder requestBuilder;
    SessionService sessionService;
    StatsService statsService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        userService = new UserService();
        requestBuilder = new RequestBuilder();
        sessionService = new SessionService();
        statsService = new StatsService();
    }

    @BeforeEach
    void createLoginUsers() {
        printService.consoleLog("unitTest", "StatsService(Server): Create and login users (prepare)");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        userService.handleRequest(request);
        Request request2 = requestBuilder.buildRequestManual(Method.POST, "/sessions", "Basic kienboec-mtcgToken", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        sessionService.handleRequest(request2);

        Request request3 = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request3.setBody("{\"username\":\"altenhof\", \"password\":\"markus\"}");
        userService.handleRequest(request3);
        Request request4 = requestBuilder.buildRequestManual(Method.POST, "/sessions", "Basic kienboec-mtcgToken", "application/json");
        request4.setBody("{\"username\":\"altenhof\", \"password\":\"markus\"}");
        sessionService.handleRequest(request4);
    }

    @Test
    void getStats() {
        printService.consoleLog("unitTest", "StatsService(Server): Get user stats");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/stats", "Basic kienboec-mtcgToken", "application/json");
        request.setBody("");
        printService.printRequest(request);
        Response response = statsService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    void getScoreboard() {
        printService.consoleLog("unitTest", "StatsService(Server): Get score-board");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/score", "Basic kienboec-mtcgToken", "application/json");
        request.setBody("");
        printService.printRequest(request);
        Response response = statsService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}