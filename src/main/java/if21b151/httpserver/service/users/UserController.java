package if21b151.httpserver.service.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Controller;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;

import java.util.List;
import java.util.Objects;

public class UserController extends Controller {
    UserService userService;
    PrintService printService;

    public UserController(UserService userService) {
        this.userService = userService;
        this.printService = new PrintServiceImpl();
    }

    public Response createUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);
            switch (this.userService.create(user)) {
                case 0:
                    printService.consoleLog("server", "Could not create - User already exists.");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "User already exists!");
                case 1:
                    printService.consoleLog("server", "New user created");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "New User created -- " + userDataJSON);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection -- " + userDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

    public Response updateUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            user.setToken(request.getHeaderMap().getHeader("Authorization"));
            user.setUsername(request.getPathParts().get(request.getPathParts().size() - 1));
            if (Objects.equals(userService.get(user).getUsername(), request.getPathParts().get(request.getPathParts().size() - 1))) {
                printService.consoleLog("server", "Updating user");
                userService.update(user);
                return new Response(HttpStatus.OK, ContentType.JSON, "{ \"message\" : \"User updated.\" }");
            } else {
                printService.consoleLog("server", "Could not update user");
                return new Response(HttpStatus.FORBIDDEN, ContentType.JSON, "{ \"message\" : \"Access denied\" }");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

    public Response getUser(Request request) {
        User user = new User();
        user.setToken(request.getHeaderMap().getHeader("Authorization"));
        user.setUsername(request.getPathParts().get(request.getPathParts().size() - 1));
        if (userService.get(user) == null) {
            printService.consoleLog("server", "Try to send user. User does not exist");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"Permission denied or user does not exist\" }");
        }
        if (!Objects.equals(userService.get(user).getUsername(), request.getPathParts().get(request.getPathParts().size() - 1))) {
            printService.consoleLog("server", "Try to send user. Permission denied.");
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
        try {
            printService.consoleLog("server", "Sending user");
            String userDataJSON = this.getObjectMapper().writeValueAsString(userService.get(user));
            return new Response(HttpStatus.OK, ContentType.JSON, "User: " + userDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }
}
