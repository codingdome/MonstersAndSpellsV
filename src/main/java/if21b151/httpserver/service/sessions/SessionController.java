package if21b151.httpserver.service.sessions;

import com.fasterxml.jackson.core.JsonProcessingException;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Controller;
import httpserver.server.Request;
import httpserver.server.Response;
import user.model.User;
import user.repository.UserRepository;

public class SessionController extends Controller {

    UserRepository userRepository;

    public SessionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response loginUser(Request request) {
        try {
            User user = this.getObjectMapper().readValue(request.getBody(), User.class);
            user.setToken("Basic " + user.getUsername() + "-mtcgToken");
            int status = this.userRepository.login(user);
            String userDataJSON = this.getObjectMapper().writeValueAsString(user);

            switch (status) {
                case 0:
                    System.out.println("loginUser: user does not exist");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No User Found");
                case 1:
                    System.out.println("loginUser: wrong password");
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Wrong Password -- ");
                case 2:
                    System.out.println("loginUser: successfully logged in");
                    return new Response(HttpStatus.ACCEPTED, ContentType.JSON, "Successfully logged in -- " + userDataJSON);
            }
            return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "No DB Connection -- " + userDataJSON);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"message\" : \"Internal Server Error\" }");
        }
    }

}
