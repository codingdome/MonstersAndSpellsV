package if21b151.httpserver.service.stats;

import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

import java.util.Objects;

public class StatsService implements Service {

    private final StatsController statsController;

    public StatsService() {
        this.statsController = new StatsController(new if21b151.application.user.service.UserServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {

        } else if (request.getMethod() == Method.GET && (Objects.equals(request.getPathParts().get(0), "stats"))) {
            return this.statsController.getStats(request);
        } else if (request.getMethod() == Method.GET && (Objects.equals(request.getPathParts().get(0), "score"))) {
            return this.statsController.getScoreboard(request);
        } else if (request.getMethod() == Method.PUT) {

        } else if (request.getMethod() == Method.POST) {

        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No Method detected.\" }");
    }

}
