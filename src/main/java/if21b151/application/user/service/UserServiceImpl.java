package if21b151.application.user.service;

import if21b151.application.user.model.Stats;
import if21b151.application.user.model.User;
import if21b151.application.user.repository.UserRepository;
import if21b151.application.user.repository.UserRepositoryImpl;

import java.util.List;

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
    public Stats getStats(String token) {
        String[] tokenSplit = token.split(" ");
        return userRepository.getStats(tokenSplit[1].split("-")[0]);
    }

    @Override
    public List<Stats> getScoreboard() {
        return userRepository.getScoreboard();
    }

    @Override
    public int update(User user) {
        userRepository.updateInformation(user);
        userRepository.updateStats(user);
        return 0;
    }
}
