package if21b151.application.user.repository;

import if21b151.application.user.model.Stats;
import if21b151.application.user.model.User;

import java.util.List;

public interface UserRepository {

    public int create(User user);

    public int login(User user);

    public User get(User user);

    public User updateInformation(User user);

    public User updateStats(User user);

    public Stats getStats(String username);

    public List<Stats> getScoreboard();

}
