package vip.marcel.gamestarbro.lobby.utils.database;

import vip.marcel.gamestarbro.lobby.Lobby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class MySQL {

    private final Lobby plugin;

    private final String hostname, database, username, password;
    private final int port;

    private Connection connection;

    public MySQL(Lobby plugin) {
        this.plugin = plugin;

        this.hostname = plugin.getDatabaseConfiguration().getString("Database.MySQL.Hostname");
        this.database = plugin.getDatabaseConfiguration().getString("Database.MySQL.Database");
        this.username = plugin.getDatabaseConfiguration().getString("Database.MySQL.Username");
        this.password = plugin.getDatabaseConfiguration().getString("Database.MySQL.Password");
        this.port = plugin.getDatabaseConfiguration().getInteger("Database.MySQL.Port");
    }

    public void connect() {
        try {
            if(this.connection == null) {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                this.plugin.getLogger().log(Level.INFO, "MySQL connection successfully opend!");
            }


            {
                PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS Players(id INT AUTO_INCREMENT PRIMARY KEY, UUID TEXT, PlayerName TEXT, IPAdress TEXT, AbuseLevel INT, KicksAmount INT, PlayTime TEXT, Coins INT, FirstJoin TEXT, LastSeen TEXT, LoginStreak INT)");
                statement.executeUpdate();
                statement.close();
            }

            {
                PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS DailyReward(id INT AUTO_INCREMENT PRIMARY KEY, UUID TEXT, TimeStamp TEXT)");
                statement.executeUpdate();
                statement.close();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(this.connection != null) {
                this.connection.close();
                this.plugin.getLogger().log(Level.INFO, "MySQL connection successfully closed!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}
