package if21b151.httpserver.service.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Controller;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;

import java.util.List;

public class UserController extends Controller {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Response createUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);
            switch (this.userService.create(user)) {
                case 0:
                    System.out.println("createUser: user already exists");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "User already exists!");
                case 1:
                    System.out.println("createUser: new user created");
                    return new Response(HttpStatus.CREATED, ContentType.JSON, "New User created -- " + userDataJSON);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection -- " + userDataJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

}
