package if21b151.httpserver.service.gameengine;

import if21b151.application.gameengine.service.GameEngineServiceImpl;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

public class GameEngineService implements Service {

    public final GameEngineController gameEngineController;

    public GameEngineService() {
        this.gameEngineController = new GameEngineController(new GameEngineServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {


        } else if (request.getMethod() == Method.GET) {

        } else if (request.getMethod() == Method.POST) {

            return this.gameEngineController.registerForBattle(request);

        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "{ \"message\" : \"No Method detected.\" }"
        );
    }
}
