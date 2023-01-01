package if21b151.application.user.repository;

import if21b151.application.user.model.User;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public int create(User user) {
        if (userExists(user)) {
            return 0;
        }
        insertUser(user);
        insertStats(user);
        return 1;
    }

    private void insertUser(User user) {
        String sql = """
                insert into users (username,password,name,bio,img,token) values(?,?,?,?,?,?)
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getBio());
            statement.setString(5, user.getImg());
            statement.setString(6, user.getToken());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertStats(User user) {
        String sql = """
                insert into stats (username,name,elo,coins,won,lost) values(?,?,?,?,?,?)
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());
            statement.setInt(3, user.getStats().getElo());
            statement.setInt(4, user.getStats().getCoins());
            statement.setInt(5, user.getStats().getWon());
            statement.setInt(6, user.getStats().getLost());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*helper function (return true if userExists)*/
    boolean userExists(User user) {
        boolean userExists = false;
        String sql = """
                select count(*) from users where username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                if (results.getInt(1) > 0) {
                    userExists = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userExists;
    }

    /*helper function (return true if userPW is correct*/
    private boolean userPassword(User user) {
        boolean pwIsCorrect = false;
        String sql = """
                select count(*) from users where username=? AND password=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                if (results.getInt(1) > 0) {
                    pwIsCorrect = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pwIsCorrect;
    }
}
