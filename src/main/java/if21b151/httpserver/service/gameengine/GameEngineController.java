package if21b151.httpserver.service.gameengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import if21b151.application.gameengine.model.BattleLog;
import if21b151.application.gameengine.service.GameEngineService;
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

public class GameEngineController extends Controller {

    GameEngineService gameEngineService;
    UserService userService = new UserServiceImpl();
    PrintService printService = new PrintServiceImpl();

    public GameEngineController(GameEngineService gameEngineService) {
        this.gameEngineService = gameEngineService;
    }

    public Response registerForBattle(Request request) {

        printService.consoleLog("server", "Register for battle");

        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        String[] tokenSplit = user.getToken().split(" ");
        String username = tokenSplit[1].split("-")[0];
        user.setUsername(username);
        User fullUser = userService.get(user);
        printService.consoleLog("server", "Registered player:");
        printService.printUser(fullUser);

        try {
            BattleLog battleLog = gameEngineService.registerForBattle(fullUser);
            String battleLogJSON = this.getObjectMapper().writeValueAsString(battleLog);

            if (battleLog.getWinner() == null) {
                return new Response(HttpStatus.OK, ContentType.JSON, "User added to lobby");
            } else {
                return new Response(HttpStatus.OK, ContentType.JSON, battleLogJSON);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

}
