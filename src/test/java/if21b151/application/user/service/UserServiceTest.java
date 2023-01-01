package if21b151.application.user.service;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}