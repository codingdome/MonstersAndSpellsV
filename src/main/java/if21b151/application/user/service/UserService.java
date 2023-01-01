package if21b151.application.user.service;

import if21b151.application.user.model.User;

public interface UserService {
    public int create(User user);

    public int login(User user);

    public User get(User user);

    public int update(User user);
}
