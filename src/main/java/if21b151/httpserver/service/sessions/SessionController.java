package if21b151.httpserver.service.sessions;

import com.fasterxml.jackson.core.JsonProcessingException;

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


public class SessionController extends Controller {

    UserService userService;
    PrintService printService;

    public SessionController(UserService userService) {
        this.userService = userService;
        this.printService = new PrintServiceImpl();
    }

    public Response loginUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);

            int status = this.userService.login(user);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);

            switch (status) {
                case 0:
                    printService.consoleLog("server", "Login - user does not exist");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No User Found.");
                case 1:
                    printService.consoleLog("server", "Login - wrong password");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Password.");
                case 2:
                    printService.consoleLog("server", "Login successful.");
                    return new Response(HttpStatus.ACCEPTED, ContentType.JSON, "Successfully logged in -- " + userDataJSON);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection -- " + userDataJSON);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

}
