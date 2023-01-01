package if21b151.httpserver.service.echo;

import if21b151.httpserver.http.ContentType;
import if21b151.httpserver.http.HttpStatus;
import if21b151.httpserver.server.Request;
import if21b151.httpserver.server.Response;
import if21b151.httpserver.server.Service;

public class EchoService implements Service {
    @Override
    public Response handleRequest(Request request) {
        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                "Echo-" + request.getBody());
    }
}
