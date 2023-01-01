package if21b151.application.user.repository;

import if21b151.application.user.model.User;

public interface UserRepository {

    public int create(User user);

    public int login(User user);

    public User get(User user);

    public User updateInformation(User user);

    public User updateStats(User user);

}
