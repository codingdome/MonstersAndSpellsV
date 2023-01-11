package if21b151.httpserver.service.users;

import if21b151.database.DataBase;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.service.sessions.SessionService;
import if21b151.httpserver.utils.RequestBuilder;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

class UserServiceTest {

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

    @Test
    void testCreateUser() {
        printService.consoleLog("unitTest", "UserService(Server): Test create new user");
        Request request = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        printService.printResponse(response);
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void testCreateUserTwice() {
        printService.consoleLog("unitTest", "UserService(Server): Test create new user");
        Request request = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        printService.printResponse(response);
        Response response2 = userService.handleRequest(request);
        printService.printResponse(response2);
        Assertions.assertEquals(400, response2.getStatus());
    }

    @Test
    void getUser() {
        printService.consoleLog("unitTest", "UserService(Server): Get user");
        Request request = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");

        userService.handleRequest(request);

        Request request2 = requestBuilder.buildRequestManual(Method.POST, "http://localhost:12345/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        sessionService.handleRequest(request2);

        Request request3 = requestBuilder.buildRequestManual(Method.GET, "http://localhost:12345/users/kienboec", "Basic kienboec-mtcgToken", "application/json");

        Response response = userService.handleRequest(request3);
        printService.printResponse(response);

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getUserWrongToken() {
        printService.consoleLog("unitTest", "UserService(Server): Get user, wrong token");
        Request request = requestBuilder.buildRequestManual(Method.POST, "users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");

        userService.handleRequest(request);

        Request request2 = requestBuilder.buildRequestManual(Method.POST, "sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        sessionService.handleRequest(request2);

        Request request3 = requestBuilder.buildRequestManual(Method.GET, "/users/kienboec", "Basic wrong-mtcgToken", "application/json");

        Response response = userService.handleRequest(request3);
        printService.printResponse(response);

        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    void getUserMissingSubString() {
        printService.consoleLog("unitTest", "UserService(Server): Get user, missing substring");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");

        userService.handleRequest(request);

        Request request2 = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        sessionService.handleRequest(request2);

        Request request3 = requestBuilder.buildRequestManual(Method.GET, "/users", "Basic wrong-mtcgToken", "application/json");

        Response response = userService.handleRequest(request3);
        printService.printResponse(response);


    }

    @Test
    void updateUserData() {
        printService.consoleLog("unitTest", "UserService(Server): Update user data");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        userService.handleRequest(request);
        Request request2 = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request2.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        sessionService.handleRequest(request2);

        Request request3 = requestBuilder.buildRequestManual(Method.PUT, "/users/kienboec", "Basic kienboec-mtcgToken", "application/json");
        request3.setBody("{\"name\": \"Kienboeck\",  \"bio\": \"me playin...\", \"img\": \":-)\"}");
        printService.printRequest(request3);
        Response response = userService.handleRequest(request3);
        printService.printResponse(response);

        Assertions.assertEquals(200, response.getStatus());

        Request request4 = requestBuilder.buildRequestManual(Method.GET, "/users/kienboec", "Basic kienboec-mtcgToken", "application/json");

        Response response1 = userService.handleRequest(request4);
        printService.printResponse(response1);
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}