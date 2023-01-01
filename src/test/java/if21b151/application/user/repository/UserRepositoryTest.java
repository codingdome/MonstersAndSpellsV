package if21b151.application.user.repository;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

class UserRepositoryTest {

    PrintService printService;
    UserRepository userRepository;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void createUser() {
        printService.consoleLog("unitTest", "UserRepository: Create New User");
        User user = new User();
        userRepository.create(user);
    }

    @Test
    void loginUser() {
        printService.consoleLog("unitTest", "UserRepository: Login User");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setToken("Basic " + user.getUsername() + "-mtcgToken");
        userRepository.create(user);
        Assertions.assertEquals(2, userRepository.login(user));
        User wrongPW = new User();
        user.setUsername("username");
        user.setPassword("wrongpassword");
        user.setToken("Basic " + user.getUsername() + "-mtcgToken");
        Assertions.assertEquals(1, userRepository.login(user));
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}