package if21b151.httpserver.service.tradings;

import com.fasterxml.jackson.core.JsonProcessingException;
import if21b151.application.trading.model.Trade;
import if21b151.application.trading.service.TradeService;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.application.user.service.UserServiceImpl;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Controller;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.util.List;

public class TradingController extends Controller {

    TradeService tradeService;
    PrintService printService;
    UserService userService;

    public TradingController(TradeService tradeService) {
        this.tradeService = tradeService;
        this.printService = new PrintServiceImpl();
        this.userService = new UserServiceImpl();
    }

    public Response getTrades(Request request) {
        printService.consoleLog("server", "Send all trades");

        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);
        User fullUser = userService.get(user);

        if (fullUser == null) {
            printService.consoleLog("server", "no user / wrong token");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"User not found / token invalid.\" }");
        }
        try {
            List<Trade> tradesData = this.tradeService.getTrades();
            if (tradesData.size() < 1) {
                return new Response(HttpStatus.OK, ContentType.JSON, "{ \"message\" : \"No Trading deals available.\" }");
            }
            String tradesDataJSON = this.getObjectMapper().writeValueAsString(tradesData);
            return new Response(HttpStatus.OK, ContentType.JSON, tradesDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

    public Response addTrade(Request request) {
        printService.consoleLog("server", "Add new trade");

        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);
        User fullUser = userService.get(user);

        if (fullUser == null) {
            printService.consoleLog("server", "no user / wrong token");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"User not found / token invalid.\" }");
        }
        try {
            Trade trade = this.getObjectMapper().readValue(request.getBody(), Trade.class);
            tradeService.addTrade(fullUser, trade);
            String tradeDataJSON = this.getObjectMapper().writeValueAsString(trade);
            return new Response(HttpStatus.OK, ContentType.JSON, tradeDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

    public Response deleteTrade(Request request) {
        printService.consoleLog("server", "Delete trade");

        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);
        User fullUser = userService.get(user);

        if (fullUser == null) {
            printService.consoleLog("server", "no user / wrong token");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"User not found / token invalid.\" }");
        }

        String tradeID = request.getPathParts().get(1);

        switch (tradeService.deleteTrade(fullUser, tradeID)) {
            case 0:
                return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"Trade successfully deleted.\" }");
            case 1:
                return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"No trade found / not authorized.\" }");
        }

        int status = tradeService.deleteTrade(fullUser, tradeID);

        return new Response(HttpStatus.OK, ContentType.JSON, "test");
    }

    public Response trade(Request request) {
        printService.consoleLog("server", "Start trade");

        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);
        User fullUser = userService.get(user);

        if (fullUser == null) {
            printService.consoleLog("server", "no user / wrong token");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"User not found / token invalid.\" }");
        }

        String tradeID = request.getPathParts().get(1);

        String cardID;
        try {
            cardID = this.getObjectMapper().readValue(request.getBody(), String.class);

            switch (tradeService.trade(user, tradeID, cardID)) {
                case 0:
                    return new Response(HttpStatus.OK, ContentType.JSON, "{ \"message\" : \"Trade successful.\" }");
                case 1:
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"Invalid trade / no trade found.\" }");
                case 2:
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"Invalid trade / trade with yourself.\" }");
                case 3:
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"Invalid trade / bad card to trade.\" }");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }

        System.out.println(tradeID);
        System.out.println(cardID);

        return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "Internal Server Error");
    }
}
