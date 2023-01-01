package if21b151.application.user.service;

import if21b151.application.user.model.User;
import if21b151.application.user.repository.UserRepository;
import if21b151.application.user.repository.UserRepositoryImpl;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public int create(User user) {
        return userRepository.create(user);
    }

    @Override
    public int login(User user) {
        user.setToken("Basic " + user.getUsername() + "-mtcgToken");
        return userRepository.login(user);
    }

    @Override
    public User get(User user) {
        return userRepository.get(user);
    }

    @Override
    public int update(User user) {
        userRepository.updateInformation(user);
        userRepository.updateStats(user);
        return 0;
    }
}
