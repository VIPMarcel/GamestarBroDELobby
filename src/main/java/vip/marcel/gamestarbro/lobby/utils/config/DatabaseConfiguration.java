package vip.marcel.gamestarbro.lobby.utils.config;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DatabaseConfiguration implements ConfigHandler {

    private final Lobby plugin;

    private final File folder = new File("plugins/Lobby");
    private final File file = new File(this.folder, "database.yml");

    private YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);

    public DatabaseConfiguration(Lobby plugin) {
        this.plugin = plugin;

        this.createConfigurationFiles();
    }

    private void createConfigurationFiles() {

        if(!this.folder.exists()) {
            this.folder.mkdir();
        }

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.configuration.set("Database.MySQL.Hostname", "localhost");
                this.configuration.set("Database.MySQL.Port", 3306);
                this.configuration.set("Database.MySQL.Database", "network");
                this.configuration.set("Database.MySQL.Username", "root");
                this.configuration.set("Database.MySQL.Password", "");
                this.configuration.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void saveConfig() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reloadConfiguration() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public String getString(String common) {
        return this.configuration.getString(common);
    }

    @Override
    public Integer getInteger(String common) {
        return this.configuration.getInt(common);
    }

    @Override
    public Double getDouble(String common) {
        return this.configuration.getDouble(common);
    }

    @Override
    public Float getFloat(String common) {
        return (float) this.configuration.getDouble(common);
    }

    @Override
    public List<String> getStringList(String common) {
        return this.configuration.getStringList(common);
    }

    @Override
    public Boolean getBoolean(String common) {
        return this.configuration.getBoolean(common);
    }

    @Override
    public Object getObject(String common, Class clazz) {
        return this.configuration.getObject(common, clazz);
    }

    @Override
    public void set(String common, Object value) {
        this.configuration.set(common, value);
    }

}
