package if21b151.application.user.repository;

import if21b151.application.user.model.Stats;
import if21b151.application.user.model.User;

import java.util.List;

public interface UserRepository {

    int create(User user);

    int login(User user);

    User get(User user);

    User updateInformation(User user);

    User updateStats(User user);

    Stats getStats(String username);

    List<Stats> getScoreboard();

}
