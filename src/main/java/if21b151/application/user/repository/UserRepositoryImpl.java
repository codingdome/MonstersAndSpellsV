package if21b151.application.user.repository;

import if21b151.application.user.model.Stats;
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

    @Override
    public int login(User user) {
        if (!userExists(user)) {
            return 0;
        }
        if (!userPassword(user)) {
            return 1;
        }
        String sql = """
                update users set token=? where username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getToken());
            statement.setString(2, user.getUsername());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 2;
    }

    @Override
    public User get(User user) {
        String sql = """
                select * from users inner join stats on users.username=stats.username where users.username=? AND token=?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getToken());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                User userData = new User();
                userData.setUsername(results.getString(1));
                userData.setPassword(results.getString(2));
                userData.setName(results.getString(3));
                userData.setBio(results.getString(4));
                userData.setImg(results.getString(5));
                userData.setToken(results.getString(6));
                userData.getStats().setElo(results.getInt(9));
                userData.getStats().setCoins(results.getInt(10));
                userData.getStats().setWon(results.getInt(11));
                userData.getStats().setLost(results.getInt(12));
                return userData;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Stats getStats(String username) {
        String sql = """
                select * from stats where username = ?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Stats stats = new Stats();
                stats.setUsername(results.getString(1));
                stats.setName(results.getString(2));
                stats.setElo(results.getInt(3));
                stats.setCoins(results.getInt(4));
                stats.setWon(results.getInt(5));
                stats.setLost(results.getInt(6));
                return stats;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User updateInformation(User user) {
        String sql = """
                update users set name = ?, bio = ?, img = ? where token=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getBio());
            statement.setString(3, user.getImg());
            statement.setString(4, user.getToken());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql_stats = """
                update stats set name = ? where username=?
                """;

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql_stats);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return get(user);
    }

    @Override
    public User updateStats(User user) {
        String sql = """
                update stats set elo = ?, coins = ?, won = ?, lost = ? where username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, user.getStats().getElo());
            statement.setInt(2, user.getStats().getCoins());
            statement.setInt(3, user.getStats().getWon());
            statement.setInt(4, user.getStats().getLost());
            statement.setString(5, user.getUsername());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return get(user);
    }

    /*helper function inserts in table users*/
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

    /*helper function (inserts in table stats)*/
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
