package if21b151.application.user.repository;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;
import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @AfterEach
    void rollbackData() throws SQLException {
        DataBase.getConnection().rollback();
    }
}