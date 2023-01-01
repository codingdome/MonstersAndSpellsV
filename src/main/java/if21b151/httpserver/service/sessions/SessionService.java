package if21b151.httpserver.service.sessions;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import user.repository.UserRepositoryImpl;

public class SessionService implements Service {

    private final SessionController sessionController;

    public SessionService() {
        this.sessionController = new SessionController(new UserRepositoryImpl());
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
