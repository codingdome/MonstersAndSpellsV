package if21b151.application.user.model;

import if21b151.utility.PrintService;
import if21b151.utility.PrintServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    PrintService printService;

    @BeforeEach
    void initServices() {
        printService = new PrintServiceImpl();
    }

    @Test
    void testConstructNewUser() {
        printService.consoleLog("unitTest", "UserTest: Test Construct New User");
        User user = new User();
        assertEquals(20, user.getStats().getCoins());
        assertEquals(1000, user.getStats().getElo());
        assertEquals(0, user.getStats().getWon());
        assertEquals(0, user.getStats().getLost());
    }

    @Test
    void testConstructSpecificUser() {
        printService.consoleLog("unitTest", "UserTest: Test Constrcut Specific User");
        User user = new User("dominik", "password", "Dominik", "bio", ":-)", "Token", 50, 0, 0, 0);
        assertEquals(50, user.getStats().getElo());
        printService.printUser(user);
    }
}