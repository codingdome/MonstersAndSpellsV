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

    @Test
    void getUser() {
        printService.consoleLog("unitTest", "UserRepository: Get User");
        User user = new User("dominik", "password", "Dominik", "bio", ".-)", "token", 1000, 20, 0, 0);
        userRepository.create(user);
        userRepository.login(user);
        printService.printUser(userRepository.get(user));
        Assertions.assertEquals(1000, userRepository.get(user).getStats().getElo());
    }

    @Test
    void updateUserInformation() {
        printService.consoleLog("unitTest", "UserRepository: Update User Informations");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        user.setToken("token");
        userRepository.create(user);
        printService.printUser(userRepository.get(user));
        user.setBio("bioText");
        user.setImg(":-)");
        user.setName("Dominik Englert");
        printService.printUser(userRepository.updateInformation(user));
        Assertions.assertEquals("bioText", userRepository.get(user).getBio());
        Assertions.assertEquals(":-)", userRepository.get(user).getImg());
    }

    @Test
    void updateUserStats() {
        printService.consoleLog("unitTest", "UserRepository: Update User Stats");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        user.setToken("token");
        userRepository.create(user);
        printService.printUser(userRepository.get(user));
        user.getStats().setLost(5);
        user.getStats().setCoins(15);
        user.getStats().setWon(2);
        user.getStats().setElo(user.getStats().getElo() + 50);
        userRepository.updateStats(user);
        printService.printUser(userRepository.get(user));
        Assertions.assertEquals(5, userRepository.get(user).getStats().getLost());
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}