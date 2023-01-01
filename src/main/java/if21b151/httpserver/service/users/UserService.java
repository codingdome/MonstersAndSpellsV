package if21b151.httpserver.service.users;

import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

public class UserService implements Service {

    private final UserController userController;

    public UserService() {
        this.userController = new UserController(new if21b151.application.user.service.UserServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {
            return this.userController.getUser(request);
        } else if (request.getMethod() == Method.GET) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"Missing substring.\" }");
        } else if (request.getMethod() == Method.PUT) {
            return this.userController.updateUser(request);
        } else if (request.getMethod() == Method.POST) {
            return this.userController.createUser(request);
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"message\" : \"No Method detected.\" }");
    }
}
