package vip.marcel.gamestarbro.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vip.marcel.gamestarbro.lobby.listeners.AsyncPlayerChatListener;
import vip.marcel.gamestarbro.lobby.listeners.PlayerJoinListener;
import vip.marcel.gamestarbro.lobby.listeners.PlayerQuitListener;
import vip.marcel.gamestarbro.lobby.utils.builders.HologramBuilder;
import vip.marcel.gamestarbro.lobby.utils.builders.ItemBuilder;
import vip.marcel.gamestarbro.lobby.utils.builders.ScoreboardBuilder;
import vip.marcel.gamestarbro.lobby.utils.colorflowmessage.ColorFlowMessageBuilder;
import vip.marcel.gamestarbro.lobby.utils.config.DatabaseConfiguration;
import vip.marcel.gamestarbro.lobby.utils.config.LocationConfiguration;
import vip.marcel.gamestarbro.lobby.utils.database.DatabasePlayers;
import vip.marcel.gamestarbro.lobby.utils.database.MySQL;
import vip.marcel.gamestarbro.lobby.utils.locations.LocationExecutor;
import vip.marcel.gamestarbro.lobby.utils.mojang.ReflectionUtil;
import vip.marcel.gamestarbro.lobby.utils.mojang.UUIDFetcher;

public final class Lobby extends JavaPlugin {

    private final String prefix = "§8§l┃ §6Lobby §8► §7";
    private final String globalPrefix = "§8§l┃ §6GamestarBro §8► §7";
    private final String noPermissions = "§cDu hast keinen Zugriff auf diesen Befehl.";
    private final String unknownCommand = "§cDieser Befehl existiert nicht.";

    private UUIDFetcher uuidFetcher;
    private ReflectionUtil reflectionUtil;

    private ColorFlowMessageBuilder colorFlowMessageBuilder;

    private LocationConfiguration locationConfiguration;
    private DatabaseConfiguration databaseConfiguration;

    private MySQL mySQL;
    private DatabasePlayers databasePlayers;

    private LocationExecutor locationExecutor;

    @Override
    public void onEnable() {
        this.init();

        Bukkit.getServer().getScheduler().runTaskTimer(this, () -> {
            this.uuidFetcher.clearCache();
        }, 12000, 12000);
    }

    @Override
    public void onDisable() {
        this.mySQL.disconnect();
    }

    private void init() {
        this.uuidFetcher = new UUIDFetcher(this);
        this.reflectionUtil = new ReflectionUtil(this);

        this.colorFlowMessageBuilder = new ColorFlowMessageBuilder(this);

        this.locationConfiguration = new LocationConfiguration(this);
        this.databaseConfiguration = new DatabaseConfiguration(this);

        this.mySQL = new MySQL(this);
        this.mySQL.connect();
        this.databasePlayers = new DatabasePlayers(this);

        this.locationExecutor = new LocationExecutor(this);

        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(this), this);

    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getGlobalPrefix() {
        return this.globalPrefix;
    }

    public String getNoPermissions() {
        return this.noPermissions;
    }

    public String getUnknownCommand() {
        return this.unknownCommand;
    }

    public HologramBuilder hologram(Location location, String... lines) {
        return new HologramBuilder(this, location, lines);
    }

    public ItemBuilder item(Material type) {
        return new ItemBuilder(this, type);
    }

    public ItemBuilder item(Material type, int amount) {
        return new ItemBuilder(this, type, amount);
    }

    public ItemBuilder item(Material type, int amount, short damage) {
        return new ItemBuilder(this, type, amount, damage);
    }

    public ScoreboardBuilder scoreboardBuilder() {
        return new ScoreboardBuilder(this);
    }

    public ScoreboardBuilder scoreboard(Player player) {
        return new ScoreboardBuilder(this, player);
    }

    public UUIDFetcher getUUIDFetcher() {
        return this.uuidFetcher;
    }

    public ReflectionUtil getReflectionUtil() {
        return this.reflectionUtil;
    }

    public ColorFlowMessageBuilder getColorFlowMessageBuilder() {
        return colorFlowMessageBuilder;
    }

    public LocationConfiguration getLocationConfiguration() {
        return this.locationConfiguration;
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return this.databaseConfiguration;
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    public DatabasePlayers getDatabasePlayers() {
        return this.databasePlayers;
    }

    public LocationExecutor getLocationExecutor() {
        return this.locationExecutor;
    }

}
