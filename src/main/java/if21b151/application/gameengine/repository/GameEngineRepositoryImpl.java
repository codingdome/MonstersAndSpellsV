package if21b151.application.gameengine.repository;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameEngineRepositoryImpl implements GameEngineRepository {

    @Override
    public boolean checkIfUserIsWaiting() {
        int count = 0;

        String sql = """
                select count (*) from battles
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addUserToWaitingList(User user) {
        String sql = """
                insert into battles (username) values(?)
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLatestUsernameWaiting() {
        String username = "";

        //get username
        String sql = """
                select * from battles limit 1
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                username = results.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //delete username
        String sql2 = """
                delete from battles where username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql2);
            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return username;
    }
}
