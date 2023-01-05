package if21b151.httpserver.service.tradings;

import if21b151.application.trading.service.TradeService;
import if21b151.application.trading.service.TradeServiceImpl;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

public class TradingService implements Service {

    private final TradingController tradingController;

    public TradingService() {
        this.tradingController = new TradingController(new TradeServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {

        } else if (request.getMethod() == Method.GET) {
            return this.tradingController.getTrades(request);
        } else if (request.getMethod() == Method.PUT) {
        } else if (request.getMethod() == Method.POST && request.getPathParts().size() > 1) {
            return this.tradingController.trade(request);
        } else if (request.getMethod() == Method.POST) {
            return this.tradingController.addTrade(request);
        } else if (request.getMethod() == Method.DELETE) {
            return this.tradingController.deleteTrade(request);
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No Method detected.\" }");
    }
}
