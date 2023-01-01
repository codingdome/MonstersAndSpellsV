package if21b151.application.user.service;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

class UserServiceTest {
    PrintService printService;
    UserService userService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
        userService = new UserServiceImpl();
    }

    @Test
    void createUser() {
        printService.consoleLog("unitTest", "UserService: Create New User");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        Assertions.assertEquals(1, userService.create(user));
        Assertions.assertEquals(0, userService.create(user));
    }

    @Test
    void loginUser() {
        printService.consoleLog("unitTest", "UserService: Create And Login New User");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        userService.create(user);
        Assertions.assertEquals(2, userService.login(user));
        user.setUsername("peter");
        Assertions.assertEquals(0, userService.login(user));
        user.setUsername("dominik");
        user.setPassword("wrongpassword");
        Assertions.assertEquals(1, userService.login(user));
    }

    @Test
    void updateUserInformation() {
        printService.consoleLog("unitTest", "UserService: Create And Update User");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        user.setToken("token");
        userService.create(user);
        printService.printUser(userService.get(user));
        User user2 = new User();
        user2.setName("Dominik Englert");
        user2.setImg(":-)");
        user2.setBio("biotext");
        user2.setToken("token");
        userService.update(user2);
        printService.printUser(userService.get(user));
        Assertions.assertEquals(":-)", userService.get(user).getImg());
        Assertions.assertEquals("Dominik Englert", userService.get(user).getName());
        Assertions.assertEquals("biotext", userService.get(user).getBio());
    }

    @Test
    void updateUserStats() {
        printService.consoleLog("unitTest", "UserService: Create And Update User Stats");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        user.setToken("token");
        userService.create(user);
        printService.printUser(userService.get(user));
        User user2 = new User();
        user2.setUsername("dominik");
        user2.getStats().setElo(2000);
        user2.getStats().setWon(10);
        user2.getStats().setLost(20);
        user2.getStats().setCoins(10);
        userService.update(user2);
        printService.printUser(userService.get(user));
        Assertions.assertEquals(2000, userService.get(user).getStats().getElo());
        Assertions.assertEquals(10, userService.get(user).getStats().getWon());
        Assertions.assertEquals(20, userService.get(user).getStats().getLost());
        Assertions.assertEquals(10, userService.get(user).getStats().getCoins());

    }

    @Test
    void getUser() {
        printService.consoleLog("unitTest", "UserService: Create And Get User");
        User user = new User();
        user.setUsername("dominik");
        user.setPassword("password");
        userService.create(user);
        userService.login(user);
        printService.printUser(userService.get(user));
        Assertions.assertEquals("dominik", userService.get(user).getUsername());
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}