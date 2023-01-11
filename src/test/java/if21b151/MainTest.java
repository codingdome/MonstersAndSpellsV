package if21b151;

import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.service.cards.CardService;
import if21b151.httpserver.service.gameengine.GameEngineService;
import if21b151.httpserver.service.rollback.RollbackService;
import if21b151.httpserver.service.sessions.SessionService;
import if21b151.httpserver.service.stats.StatsService;
import if21b151.httpserver.service.tradings.TradingService;
import if21b151.httpserver.service.users.UserService;
import if21b151.httpserver.utils.RequestBuilder;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
    UserService userService;
    TradingService tradingService;
    StatsService statsService;
    SessionService sessionService;
    RollbackService rollbackService;
    GameEngineService gameEngineService;
    CardService cardService;
    RequestBuilder requestBuilder;
    PrintService printService;

    @BeforeEach
    void initServices() {
        userService = new UserService();
        tradingService = new TradingService();
        statsService = new StatsService();
        sessionService = new SessionService();
        rollbackService = new RollbackService();
        gameEngineService = new GameEngineService();
        cardService = new CardService();
        requestBuilder = new RequestBuilder();
        printService = new PrintServiceImpl();
    }

    @Test
    @Order(1)
    void printMaintestAndClearDB() {
        printService.consoleLog("unitTest", "C U R L");
        System.out.println(" ------------------------- ");
        System.out.println("|                         |");
        System.out.println("|        MONSTERS         |");
        System.out.println("|           AND           |");
        System.out.println("|         SPELLS          |");
        System.out.println("|            V            |");
        System.out.println("|                         |");
        System.out.println("|        (v.0.0.5)        |");
        System.out.println("|                         |");
        System.out.println(" -------------------------");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/rollback", "Basic admin-mtcgToken", "");
        rollbackService.handleRequest(request);
    }

    @Test
    @Order(2)
    void createNewUserKienboec() {
        printService.consoleLog("message", "create new user: kienboec");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(3)
    void createNewUserAltenhof() {
        printService.consoleLog("message", "create new user: altenhof");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"altenhof\", \"password\":\"markus\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(4)
    void createNewUserAdmin() {
        printService.consoleLog("message", "create new user: admin");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"admin\", \"password\":\"istrator\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(5)
    void createNewUserThatExists() {
        printService.consoleLog("message", "create new user: admin that already exists");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/users", "", "application/json");
        request.setBody("{\"username\":\"admin\", \"password\":\"ololo\"}");
        printService.printRequest(request);
        Response response = userService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(6)
    void loginUserKienboec() {
        printService.consoleLog("message", "login user: kienboec");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");
        printService.printRequest(request);
        Response response = sessionService.handleRequest(request);
        Assertions.assertEquals(202, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(7)
    void loginUserAltenhof() {
        printService.consoleLog("message", "login user: altenhof");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request.setBody("{\"username\":\"altenhof\", \"password\":\"markus\"}");
        printService.printRequest(request);
        Response response = sessionService.handleRequest(request);
        Assertions.assertEquals(202, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(8)
    void loginUserAdmin() {
        printService.consoleLog("message", "login user: admin");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request.setBody("{\"username\":\"admin\", \"password\":\"istrator\"}");
        printService.printRequest(request);
        Response response = sessionService.handleRequest(request);
        Assertions.assertEquals(202, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(9)
    void loginUserWrongPW() {
        printService.consoleLog("message", "login user: admin with wrong pw");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request.setBody("{\"username\":\"admin\", \"password\":\"wrong\"}");
        printService.printRequest(request);
        Response response = sessionService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(10)
    void loginUserThatNotExists() {
        printService.consoleLog("message", "login user: admin with wrong pw");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/sessions", "", "application/json");
        request.setBody("{\"username\":\"lulu\", \"password\":\"wrong\"}");
        printService.printRequest(request);
        Response response = sessionService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(11)
    void createPackageOne() {
        printService.consoleLog("message", "create package by admin #1");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"name\":\"WaterGoblin\", \"damage\":10.0}, {\"id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"name\":\"Dragon\",\"damage\": 50.0}, {\"id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"name\":\"WaterSpell\", \"damage\": 20.0}, {\"id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"name\":\"Ork\", \"damage\": 45.0}, {\"id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"name\":\"FireSpell\", \"damage\":25.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(12)
    void createPackageTwo() {
        printService.consoleLog("message", "create package by admin #2");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"644808c2-f87a-4600-b313-122b02322fd5\", \"name\":\"WaterGoblin\", \"damage\":9.0}, {\"id\":\"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"name\":\"Dragon\",\"damage\": 55.0}, {\"id\":\"91a6471b-1426-43f6-ad65-6fc473e16f9f\", \"name\":\"WaterSpell\", \"damage\": 21.0}, {\"id\":\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\", \"name\":\"Ork\", \"damage\": 55.0},{\"id\":\"f8043c23-1534-4487-b66b-238e0c3c39b5\", \"name\":\"WaterSpell\",\"damage\": 23.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(13)
    void createPackageThree() {
        printService.consoleLog("message", "create package by admin #3");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"b017ee50-1c14-44e2-bfd6-2c0c5653a37c\", \"name\":\"WaterGoblin\", \"damage\":11.0}, {\"id\":\"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"name\":\"Dragon\",\"damage\": 70.0}, {\"id\":\"88221cfe-1f84-41b9-8152-8e36c6a354de\", \"name\":\"WaterSpell\", \"damage\": 22.0}, {\"id\":\"1d3f175b-c067-4359-989d-96562bfa382c\", \"name\":\"Ork\", \"damage\": 40.0}, {\"id\":\"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\", \"name\":\"RegularSpell\", \"damage\":28.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(14)
    void createPackageFour() {
        printService.consoleLog("message", "create package by admin #4");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8\", \"name\":\"WaterGoblin\", \"damage\":10.0}, {\"id\":\"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"name\":\"Dragon\",\"damage\": 50.0}, {\"id\":\"55ef46c4-016c-4168-bc43-6b9b1e86414f\", \"name\":\"WaterSpell\", \"damage\": 20.0}, {\"id\":\"f3fad0f2-a1af-45df-b80d-2e48825773d9\", \"name\":\"Ork\", \"damage\": 45.0}, {\"id\":\"8c20639d-6400-4534-bd0f-ae563f11f57a\", \"name\":\"WaterSpell\",\"damage\":25.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(15)
    void createPackageFive() {
        printService.consoleLog("message", "create package by admin #5");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"name\":\"WaterGoblin\", \"damage\":9.0}, {\"id\":\"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"name\":\"Dragon\",\"damage\": 55.0}, {\"id\":\"2c98cd06-518b-464c-b911-8d787216cddd\", \"name\":\"WaterSpell\", \"damage\": 21.0}, {\"id\":\"951e886a-0fbf-425d-8df5-af2ee4830d85\", \"name\":\"Ork\", \"damage\": 55.0}, {\"id\":\"dcd93250-25a7-4dca-85da-cad2789f7198\", \"name\":\"FireSpell\",\"damage\":23.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(16)
    void createPackageSix() {
        printService.consoleLog("message", "create package by admin #6");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\": \"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"name\":\"WaterGoblin\", \"damage\": 11.0}, {\"id\":\"9e8238a4-8a7a-487f-9f7d-a8c97899eb48\", \"name\":\"Dragon\",\"damage\": 70.0}, {\"id\":\"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"name\":\"WaterSpell\", \"damage\": 22.0}, {\"id\":\"fc305a7a-36f7-4d30-ad27-462ca0445649\", \"name\":\"Ork\", \"damage\": 40.0}, {\"id\":\"84d276ee-21ec-4171-a509-c1b88162831c\", \"name\":\"RegularSpell\", \"damage\":28.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(17)
    void createPackageWrongToken() {
        printService.consoleLog("message", "create package by admin with wrong token");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic wrong-mtcgToken", "application/json");
        request.setBody("[{\"id\": \"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"name\":\"WaterGoblin\", \"damage\": 11.0}, {\"id\":\"9e8238a4-8a7a-487f-9f7d-a8c97899eb48\", \"name\":\"Dragon\",\"damage\": 70.0}, {\"id\":\"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"name\":\"WaterSpell\", \"damage\": 22.0}, {\"id\":\"fc305a7a-36f7-4d30-ad27-462ca0445649\", \"name\":\"Ork\", \"damage\": 40.0}, {\"id\":\"84d276ee-21ec-4171-a509-c1b88162831c\", \"name\":\"RegularSpell\", \"damage\":28.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(401, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(18)
    void acquirePackagesKienboec1() {
        printService.consoleLog("message", "acquire packages by kienboec #1");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic kienboec-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(19)
    void acquirePackagesKienboec2() {
        printService.consoleLog("message", "acquire packages by kienboec #2");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic kienboec-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(20)
    void acquirePackagesKienboec3() {
        printService.consoleLog("message", "acquire packages by kienboec #3");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic kienboec-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(21)
    void acquirePackagesKienboec4() {
        printService.consoleLog("message", "acquire packages by kienboec #4");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic kienboec-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(22)
    void acquirePackagesKienboecNoMoney() {
        printService.consoleLog("message", "acquire packages by kienboec #5 -> should fail no money");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic kienboec-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(23)
    void acquirePackagesAltenhof() {
        printService.consoleLog("message", "acquire packages by altenhof #1");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(24)
    void acquirePackagesAltenhof2() {
        printService.consoleLog("message", "acquire packages by altenhof #2");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(25)
    void acquirePackagesAltenhof3() {
        printService.consoleLog("message", "acquire packages by altenhof #3 --> shouold fail no packages");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(26)
    void createPackageSeven() {
        printService.consoleLog("message", "create package by admin #7");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"67f9048f-99b8-4ae4-b866-d8008d00c53d\", \"name\":\"WaterGoblin\", \"damage\":10.0}, {\"id\":\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"name\":\"RegularSpell\", \"damage\": 50.0}, {\"id\":\"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"name\":\"Knight\", \"damage\": 20.0}, {\"id\":\"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\", \"name\":\"RegularSpell\", \"damage\": 45.0},{\"id\":\"2508bf5c-20d7-43b4-8c77-bc677decadef\", \"name\":\"FireElf\",\"damage\": 25.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(27)
    void createPackageEight() {
        printService.consoleLog("message", "create package by admin #8");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"70962948-2bf7-44a9-9ded-8c68eeac7793\", \"name\":\"WaterGoblin\", \"damage\":9.0}, {\"id\":\"74635fae-8ad3-4295-9139-320ab89c2844\", \"name\":\"FireSpell\",\"damage\": 55.0}, {\"id\":\"ce6bcaee-47e1-4011-a49e-5a4d7d4245f3\", \"name\":\"Knight\", \"damage\": 21.0}, {\"id\":\"a6fde738-c65a-4b10-b400-6fef0fdb28ba\",\"name\":\"FireSpell\", \"damage\": 55.0}, {\"id\":\"a1618f1e-4f4c-4e09-9647-87e16f1edd2d\", \"name\":\"FireElf\", \"damage\":23.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(28)
    void createPackageNine() {
        printService.consoleLog("message", "create package by admin #9");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/packages", "Basic admin-mtcgToken", "application/json");
        request.setBody("[{\"id\":\"2272ba48-6662-404d-a9a1-41a9bed316d9\", \"name\":\"WaterGoblin\", \"damage\":11.0}, {\"id\":\"3871d45b-b630-4a0d-8bc6-a5fc56b6a043\", \"name\":\"Dragon\",\"damage\": 70.0}, {\"id\":\"166c1fd5-4dcb-41a8-91cb-f45dcd57cef3\", \"name\":\"Knight\", \"damage\": 22.0}, {\"id\":\"237dbaef-49e3-4c23-b64b-abf5c087b276\",\"name\":\"WaterSpell\", \"damage\": 40.0}, {\"id\":\"27051a20-8580-43ff-a473-e986b52f297a\", \"name\":\"FireElf\", \"damage\": 28.0}]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(29)
    void acquirePackagesAltenhof4() {
        printService.consoleLog("message", "acquire packages by altenhof #4");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(30)
    void acquirePackagesAltenhof5() {
        printService.consoleLog("message", "acquire packages by altenhof #5");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(200, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(31)
    void acquirePackagesAltenhof6() {
        printService.consoleLog("message", "acquire packages by altenhof #5 --> should fail no money");
        Request request = requestBuilder.buildRequestManual(Method.POST, "/transactions/packages", "Basic altenhof-mtcgToken", "application/json");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(32)
    void showAllAcquiredCardsKienboec() {
        printService.consoleLog("message", "show all acquired cards by kienboec");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/cards", "Basic kienboec-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(33)
    void showAllAcquiredCardsNoToken() {
        printService.consoleLog("message", "show all acquired cards without token");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/cards", "wrongToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
//        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(34)
    void showAllAcquiredCardsAltenhof() {
        printService.consoleLog("message", "show all acquired cards by altenhof");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/cards", "Basic altenhof-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(35)
    void showUnconfiguredDeckKienboec() {
        printService.consoleLog("message", "show unconfig deck by kienboec");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/deck", "Basic kienboec-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(36)
    void showUnconfiguredDeckAltenhof() {
        printService.consoleLog("message", "show unconfig deck by altenhof");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/deck", "Basic kienboec-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(36)
    void configureDeckKienboec() {
        printService.consoleLog("message", "configure deck by kienboec");
        Request request = requestBuilder.buildRequestManual(Method.PUT, "/deck", "Basic kienboec-mtcgToken", "application/json");
        request.setBody("[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\",\"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\"]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(37)
    void showConfiguredDeckKienboec() {
        printService.consoleLog("message", "show configured deck by kienboec");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/deck", "Basic kienboec-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(38)
    void configureDeckAltenhof() {
        printService.consoleLog("message", "configure deck by altenhof");
        Request request = requestBuilder.buildRequestManual(Method.PUT, "/deck", "Basic altenhof-mtcgToken", "application/json");
        request.setBody("[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\"]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(39)
    void showConfiguredDeckAltenhof() {
        printService.consoleLog("message", "show configured deck by altenhof");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/deck", "Basic altenhof-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(40)
    void reConfigureDeckAltenhof() {
        printService.consoleLog("message", "REconfigure deck by altenhof --> should fail");
        Request request = requestBuilder.buildRequestManual(Method.PUT, "/deck", "Basic altenhof-mtcgToken", "application/json");
        request.setBody("[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\"]");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(400, response.getStatus());
        printService.printResponse(response);
    }

    @Test
    @Order(41)
    void showOldConfiguredDeckAltenhof() {
        printService.consoleLog("message", "show configured deck by altenhof (should be old one)");
        Request request = requestBuilder.buildRequestManual(Method.GET, "/deck", "Basic altenhof-mtcgToken", "");
        printService.printRequest(request);
        Response response = cardService.handleRequest(request);
        Assertions.assertEquals(201, response.getStatus());
        printService.printResponse(response);
    }
}