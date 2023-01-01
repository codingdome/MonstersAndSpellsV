package if21b151.httpserver.service.sessions;

import if21b151.application.user.service.UserServiceImpl;
import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.http.Method;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

public class SessionService implements Service {

    private final SessionController sessionController;

    public SessionService() {
        this.sessionController = new SessionController(new UserServiceImpl());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET &&
                request.getPathParts().size() > 1) {


        } else if (request.getMethod() == Method.GET) {

        } else if (request.getMethod() == Method.POST) {

            return this.sessionController.loginUser(request);

        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "{ \"message\" : \"No Method detected.\" }"
        );
    }
}
