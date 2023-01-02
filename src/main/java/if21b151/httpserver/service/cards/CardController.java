package if21b151.httpserver.service.cards;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardController extends Controller {
    PrintService printService;
    CardService cardService;
    UserService userService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
        this.printService = new PrintServiceImpl();
        this.userService = new UserServiceImpl();
    }

    public Response acquirePackage(Request request) {
        if (request.getHeaderMap().getHeader("Authorization") == null) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No authorization token found!\" }");
        }
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);

        switch (cardService.acquirePackage(userService.get(user))) {
            case 0:
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No money!\" }");
            case 1:
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No Packages left!\" }");
            case 2:
                return new Response(HttpStatus.OK, ContentType.JSON, "{ \"message\" : \"Cards have been acquired!\" }");
            default:
                return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error!\" }");
        }
    }

    public Response createPackage(Request request) {

        if (!Objects.equals(request.getHeaderMap().getHeader("Authorization"), "Basic admin-mtcgToken")) {
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"Unauthorized!\" }");
        }

        try {
            printService.consoleLog("server", "Creating card package");

            Card[] cardData = this.getObjectMapper().readValue(request.getBody(), Card[].class);

            for (Card card : cardData) {
                printService.printCard(card);
            }

            CardPackage cardPackage = cardService.cardsToCardPackage(cardData);

            cardService.addNewPackage(cardPackage);

            String cardPackageData = this.getObjectMapper().writeValueAsString(cardPackage);

            return new Response(HttpStatus.CREATED, ContentType.JSON, cardPackageData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Response getAllUserCards(Request request) {

        if (request.getHeaderMap().getHeader("Authorization") == null) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No token found!\" }");
        }

        try {
            printService.consoleLog("server", "Send all cards of user");

            User user = new User();
            user.setToken(request.getHeaderMap().getHeader("Authorization"));
            String[] tokenSplit = user.getToken().split(" ");
            String username = tokenSplit[1].split("-")[0];
            user.setUsername(username);

            List<Card> userCards = cardService.getAllUserCards(user);

            String userCardsData = this.getObjectMapper().writeValueAsString(userCards);

            return new Response(HttpStatus.CREATED, ContentType.JSON, userCardsData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Response getUserDeck(Request request) {

        if (request.getHeaderMap().getHeader("Authorization") == null) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No token found!\" }");
        }

        try {
            printService.consoleLog("server", "Send all deck cards of user");

            User user = new User();
            user.setToken(request.getHeaderMap().getHeader("Authorization"));
            String[] tokenSplit = user.getToken().split(" ");
            String username = tokenSplit[1].split("-")[0];
            user.setUsername(username);

            List<Card> userDeckCards = cardService.getUserDeck(user);

            String userDeckCardsData = this.getObjectMapper().writeValueAsString(userDeckCards);

            return new Response(HttpStatus.CREATED, ContentType.JSON, userDeckCardsData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
