package if21b151.httpserver.server;

import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    PrintService printService;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
    }

    @Test
    void testGetParamsWithId() {
        printService.consoleLog("unitTest", "RequestTest: Get Params With Id");
        Request request = new Request();
        request.setPathname("/echo/1");
        request.setParams("id=99");

        assertEquals("id=99", request.getParams());
    }

    @Test
    void testGetServiceRouteWithSlash() {
        printService.consoleLog("unitTest", "RequestTest: Get Service Route With Slash");
        Request request = new Request();
        request.setPathname("/");

        assertNull(request.getServiceRoute());
    }

    @Test
    void testGetServiceRouteWithRoute() {
        printService.consoleLog("unitTest", "RequestTest: Get Service Route With Route");
        Request request = new Request();
        request.setPathname("/echo");

        assertEquals("/echo", request.getServiceRoute());
    }

    @Test
    void testGetServiceRouteWithSubRoute() {
        printService.consoleLog("unitTest", "RequestTest: Get Service Route With Sub Route");
        Request request = new Request();
        request.setPathname("/echo/1/cards");

        assertEquals("/echo", request.getServiceRoute());
        assertEquals("1", request.getPathParts().get(1));
        assertEquals("cards", request.getPathParts().get(2));
    }

}