package if21b151.application.user.model;

public class User {

    /*definitions*/
    String username;
    String password;
    String name;
    String bio;
    String img;
    String token;
    Stats stats;

    /*constr*/
    public User() {
        this.username = "";
        this.password = "";
        this.name = "-";
        this.token = "-";
        this.bio = "-";
        this.img = "-";
        this.stats = new Stats();
    }

    public User(String username, String password, String name, String bio, String img, String token, int elo, int coins, int won, int lost) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.img = img;
        this.token = token;
        this.stats = new Stats(username, name, elo, coins, won, lost);
    }

    /*get/set*/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}


