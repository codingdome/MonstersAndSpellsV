package if21b151.application.gameengine.repository;

import if21b151.application.user.model.User;

public interface GameEngineRepository {

    public boolean checkIfUserIsWaiting();

    public void addUserToWaitingList(User user);

    public User getLatestUserWaiting();
}
