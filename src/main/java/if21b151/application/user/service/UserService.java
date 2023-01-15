package if21b151.application.user.service;

import if21b151.application.user.model.Stats;
import if21b151.application.user.model.User;

import java.util.List;

public interface UserService {
    int create(User user);

    int login(User user);

    User get(User user);

    Stats getStats(String token);

    List<Stats> getScoreboard();

    int update(User user);
}
