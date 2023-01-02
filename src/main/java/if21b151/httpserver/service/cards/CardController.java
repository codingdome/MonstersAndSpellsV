package if21b151.httpserver.service.cards;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Controller;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.util.Objects;

public class CardController extends Controller {
    PrintService printService;
    CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
        this.printService = new PrintServiceImpl();
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


}
