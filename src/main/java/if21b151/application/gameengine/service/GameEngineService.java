package if21b151.application.gameengine.service;

import if21b151.application.gameengine.model.BattleLog;
import if21b151.application.user.model.User;

public interface GameEngineService {
    public BattleLog registerForBattle(User user);
}
