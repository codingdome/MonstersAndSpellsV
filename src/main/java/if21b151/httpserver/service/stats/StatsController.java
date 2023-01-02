package if21b151.httpserver.service.stats;

import com.fasterxml.jackson.core.JsonProcessingException;
import if21b151.application.user.model.Stats;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Controller;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

public class StatsController extends Controller {
    UserService userService;
    PrintService printService;

    public StatsController(UserService userService) {
        this.userService = userService;
        this.printService = new PrintServiceImpl();
    }

    public Response getStats(Request request) {
        String token = request.getHeaderMap().getHeader("Authorization");
        Stats stats = userService.getStats(token);
        if (stats == null) {
            printService.consoleLog("server", "Send user stats. failed.");
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"User not found / token invalid.\" }");
        }
        try {
            printService.consoleLog("server", "Send user stats.");
            String statsDataJSON = this.getObjectMapper().writeValueAsString(stats);
            return new Response(HttpStatus.OK, ContentType.JSON, statsDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }
}
