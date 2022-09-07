package vip.marcel.gamestarbro.lobby.utils.database;

import vip.marcel.gamestarbro.lobby.Lobby;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseDailyReward {

    private final Lobby plugin;

    public DatabaseDailyReward(Lobby plugin) {
        this.plugin = plugin;
    }

    public boolean doesPlayerExists(UUID uuid) {
        try {
            PreparedStatement statement = this.plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM DailyReward WHERE UUID = ?");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
                return (resultSet.getString("UUID") != null);

            statement.close();
            resultSet.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(UUID uuid) {
        if(!doesPlayerExists(uuid)) {
            try {
                PreparedStatement statement = this.plugin.getMySQL().getConnection().prepareStatement("INSERT INTO DailyReward(UUID, TimeStamp) VALUES (?, ?)");
                statement.setString(1, uuid.toString());
                statement.setLong(2, -1);
                statement.executeUpdate();
                statement.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deletePlayer(UUID uuid) {
        if(doesPlayerExists(uuid)) {
            try {
                PreparedStatement statement = this.plugin.getMySQL().getConnection().prepareStatement("DELETE FROM DailyReward WHERE UUID = ?");
                statement.setString(1, uuid.toString());
                statement.executeUpdate();
                statement.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public long getTimeStamp(UUID uuid) {
        try {
            PreparedStatement statement = this.plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM DailyReward WHERE UUID = ?");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getLong("TimeStamp");
            }

            statement.close();
            resultSet.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setTimeStamp(UUID uuid, long timeStamp) {
        try {
            PreparedStatement statement = this.plugin.getMySQL().getConnection().prepareStatement("UPDATE DailyReward SET TimeStamp = ? WHERE UUID = ?");
            statement.setLong(1, timeStamp);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
