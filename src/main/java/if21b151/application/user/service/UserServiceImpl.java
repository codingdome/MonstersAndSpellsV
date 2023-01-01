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
}
