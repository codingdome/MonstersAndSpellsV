package if21b151.httpserver.utils;

import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.HeaderMap;
import if21b151.httpserver.server.Request;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RequestBuilderTest {
    PrintService printService;
    RequestBuilder requestBuilder;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        requestBuilder = new RequestBuilder();
    }

    @Test
    void testBuildRequestFromGet() throws Exception {
        printService.consoleLog("unitTest", "RequestBuilderTest: Test Build Request From Get");
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: token");
        headerMap.ingest("Content-Type: application/json");
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setUrlContent("http://localhost:12345/users");
        request.setHeaderMap(headerMap);


        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");

        printService.printRequest(request);
    }

    @Test
    void buildRequestManual() {
        printService.consoleLog("unitTest", "RequestBuilderTest: Test Build Request Manual");
        Request request = requestBuilder.buildRequestManual(Method.POST, "url/ordner", "token", "contentType");
        request.setBody("body");
        Assertions.assertEquals("url/ordner", request.getUrlContent());
        Assertions.assertEquals("ordner", request.getPathParts().get(1));
        Assertions.assertEquals("body", request.getBody());
        printService.printRequest(request);
    }
}