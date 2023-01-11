package if21b151.httpserver.service.sessions;

import if21b151.database.DataBase;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.service.users.UserService;
import if21b151.httpserver.utils.RequestBuilder;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    PrintService printService;
    UserService userService;
    RequestBuilder requestBuilder;
    SessionService sessionService;

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
    }

    @BeforeEach
    void createUser() {

        Request request = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request);
        userService.handleRequest(request);
    }

    @Test
    void loginUser() {

        printService.consoleLog("unitTest", "UserService(Server): login user");
        Request request2 = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request2);
        Response response = sessionService.handleRequest(request2);
        printService.printResponse(response);
        Assertions.assertEquals(202, response.getStatus());
    }

    @Test
    void loginUserWrongPw() {

        printService.consoleLog("unitTest", "UserService(Server): login user with wrong pw");
        Request request2 = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"falsch\"}");
        printService.printRequest(request2);
        Response response = sessionService.handleRequest(request2);
        printService.printResponse(response);
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void noMethod() {
        printService.consoleLog("unitTest", "UserService(Server): login user no method");
        Request request2 = requestBuilder.buildRequestManual(null, "http://localhost:12345/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"falsch\"}");
        printService.printRequest(request2);
        Response response = sessionService.handleRequest(request2);
        printService.printResponse(response);
        Assertions.assertEquals(400, response.getStatus());
    }


    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}