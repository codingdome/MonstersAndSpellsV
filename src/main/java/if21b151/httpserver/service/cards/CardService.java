package if21b151.httpserver.service.cards;

import if21b151.application.card.service.CardServiceImpl;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

import java.util.Objects;

public class CardService implements Service {
    private final CardController cardController;

    public CardService() {
        this.cardController = new CardController(new CardServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {


        } else if (request.getMethod() == Method.GET && Objects.equals(request.getPathParts().get(0), "cards")) {

            return this.cardController.getAllUserCards(request);

        } else if (request.getMethod() == Method.GET && Objects.equals(request.getPathParts().get(0), "deck")) {

            return this.cardController.getUserDeck(request);

        } else if (request.getMethod() == Method.POST && request.getPathParts().size() > 1) {

            return this.cardController.acquirePackage(request);

        } else if (request.getMethod() == Method.POST) {

            return this.cardController.createPackage(request);

        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "[]");
    }
}
