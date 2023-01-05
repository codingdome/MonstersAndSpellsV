package if21b151.application.gameengine.repository;

import if21b151.application.user.model.User;
import if21b151.application.user.service.UserService;
import if21b151.application.user.service.UserServiceImpl;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.SQLException;


class GameEngineRepositoryTest {
    PrintService printService;
    GameEngineRepository gameEngineRepository;
    UserService userService;

    @BeforeAll
    static void configureDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void initServices() {
        this.printService = new PrintServiceImpl();
        this.gameEngineRepository = new GameEngineRepositoryImpl();
        this.userService = new UserServiceImpl();
    }

    @Test
    void checkIfUserIsWaiting() {
        printService.consoleLog("unitTest", "GameEngineRepositoryTest: check if user is waiting");
        User user = new User();
        user.setUsername("username");
        user.setToken("userToken");
        user.setPassword("password");
        gameEngineRepository.addUserToWaitingList(user);
        Assertions.assertEquals(true, gameEngineRepository.checkIfUserIsWaiting());
    }

    @Test
    void getLatestUserWaiting() {
        printService.consoleLog("unitTest", "GameEngineRepositoryTest: get latest user waiting");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);
        userService.login(user);
        gameEngineRepository.addUserToWaitingList(user);

        Assertions.assertEquals(true, gameEngineRepository.checkIfUserIsWaiting());
        User getUser = gameEngineRepository.getLatestUserWaiting();
        Assertions.assertEquals("username", getUser.getUsername());
        Assertions.assertEquals("password", getUser.getPassword());
        printService.printUser(getUser);
        Assertions.assertEquals(false, gameEngineRepository.checkIfUserIsWaiting());
    }

    @Test
    void addUserToWaitingList() {
        printService.consoleLog("unitTest", "GameEngineRepositoryTest: add user to waiting list");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userService.create(user);
        userService.login(user);
        gameEngineRepository.addUserToWaitingList(user);

        Assertions.assertEquals(true, gameEngineRepository.checkIfUserIsWaiting());
        Assertions.assertEquals("username", gameEngineRepository.getLatestUserWaiting().getUsername());
    }

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }

}