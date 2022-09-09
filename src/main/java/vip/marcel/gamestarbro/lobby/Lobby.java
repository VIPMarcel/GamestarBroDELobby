package vip.marcel.gamestarbro.lobby;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vip.marcel.gamestarbro.lobby.commands.*;
import vip.marcel.gamestarbro.lobby.listeners.*;
import vip.marcel.gamestarbro.lobby.utils.InventoryHandler;
import vip.marcel.gamestarbro.lobby.utils.ItemHandler;
import vip.marcel.gamestarbro.lobby.utils.battlebox.BattleBoxGame;
import vip.marcel.gamestarbro.lobby.utils.battlebox.BattleBoxMoveListener;
import vip.marcel.gamestarbro.lobby.utils.builders.HologramBuilder;
import vip.marcel.gamestarbro.lobby.utils.builders.ItemBuilder;
import vip.marcel.gamestarbro.lobby.utils.challanger.ChallangerGameType;
import vip.marcel.gamestarbro.lobby.utils.colorflowmessage.ColorFlowMessageBuilder;
import vip.marcel.gamestarbro.lobby.utils.config.DatabaseConfiguration;
import vip.marcel.gamestarbro.lobby.utils.config.LocationConfiguration;
import vip.marcel.gamestarbro.lobby.utils.database.DatabaseDailyReward;
import vip.marcel.gamestarbro.lobby.utils.database.DatabasePlayers;
import vip.marcel.gamestarbro.lobby.utils.database.MySQL;
import vip.marcel.gamestarbro.lobby.utils.jumpandrun.JumpAndRunGame;
import vip.marcel.gamestarbro.lobby.utils.jumpandrun.JumpAndRunMoveListener;
import vip.marcel.gamestarbro.lobby.utils.locations.LocationExecutor;
import vip.marcel.gamestarbro.lobby.utils.mojang.ReflectionUtil;
import vip.marcel.gamestarbro.lobby.utils.mojang.UUIDFetcher;
import vip.marcel.gamestarbro.lobby.utils.placeholders.NetworkExpansions;
import vip.marcel.gamestarbro.lobby.utils.runnables.UpdateTopPlayersWallRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Lobby extends JavaPlugin {

    private final String prefix = "§8§l┃ §6Lobby §8► §7";
    private final String globalPrefix = "§8§l┃ §6GamestarBro §8► §7";
    private final String noPermissions = "§cDu hast keinen Zugriff auf diesen Befehl.";
    private final String unknownCommand = "§cDieser Befehl existiert nicht.";

    private List<Player> editMode, flyMode, setupJnRCooldown, interactCooldown, challangerToggled, setupPlayerWallCooldown;

    private Map<Player, Integer> setupJnR, setupPlayerWall;
    private Map<Player, Player> challanger, challangerInteracted;
    private Map<Player, ChallangerGameType> challangerGameType;

    private UUIDFetcher uuidFetcher;
    private ReflectionUtil reflectionUtil;

    private ColorFlowMessageBuilder colorFlowMessageBuilder;

    private LocationConfiguration locationConfiguration;
    private DatabaseConfiguration databaseConfiguration;

    private MySQL mySQL;
    private DatabasePlayers databasePlayers;
    private DatabaseDailyReward databaseDailyReward;

    private LocationExecutor locationExecutor;

    private JumpAndRunGame jumpAndRunGame;
    private BattleBoxGame battleBoxGame;

    private ItemHandler itemHandler;
    private InventoryHandler inventoryHandler;

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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
        this.editMode = Lists.newArrayList();
        this.flyMode = Lists.newArrayList();
        this.setupJnRCooldown = Lists.newArrayList();
        this.interactCooldown = Lists.newArrayList();
        this.challangerToggled = Lists.newArrayList();
        this.setupPlayerWallCooldown = Lists.newArrayList();

        this.setupJnR = Maps.newHashMap();
        this.setupPlayerWall = Maps.newHashMap();
        this.challanger = Maps.newHashMap();
        this.challangerInteracted = Maps.newHashMap();
        this.challangerGameType = Maps.newHashMap();

        this.uuidFetcher = new UUIDFetcher(this);
        this.reflectionUtil = new ReflectionUtil(this);

        this.colorFlowMessageBuilder = new ColorFlowMessageBuilder(this);

        this.locationConfiguration = new LocationConfiguration(this);
        this.databaseConfiguration = new DatabaseConfiguration(this);

        this.mySQL = new MySQL(this);
        this.mySQL.connect();
        this.databasePlayers = new DatabasePlayers(this);
        this.databaseDailyReward = new DatabaseDailyReward(this);

        this.locationExecutor = new LocationExecutor(this);

        this.jumpAndRunGame = new JumpAndRunGame(this);
        this.battleBoxGame = new BattleBoxGame(this);

        this.itemHandler = new ItemHandler(this);
        this.inventoryHandler = new InventoryHandler(this);

        new NetworkExpansions(this).register();

        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new BlockBurnListener(this), this);
        pluginManager.registerEvents(new BlockPhysicsListener(this), this);
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        pluginManager.registerEvents(new CraftItemListener(this), this);
        pluginManager.registerEvents(new EntityChangeBlockListener(this), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(this), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(this), this);
        pluginManager.registerEvents(new HangingBreakListener(this), this);
        pluginManager.registerEvents(new HangingPlaceListener(this), this);
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new LeavesDecayListener(this), this);
        pluginManager.registerEvents(new PlayerArmorStandManipulateListener(this), this);
        pluginManager.registerEvents(new PlayerBedEnterListener(this), this);
        pluginManager.registerEvents(new PlayerBucketEmptyListener(this), this);
        pluginManager.registerEvents(new PlayerBucketFillListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new PlayerDropItemListener(this), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new PlayerItemHeldListener(this), this);
        pluginManager.registerEvents(new PlayerPickupItemListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerRespawnListener(this), this);
        pluginManager.registerEvents(new PlayerShearEntityListener(this), this);
        pluginManager.registerEvents(new PlayerSwapHandItemsListener(this), this);
        pluginManager.registerEvents(new PlayerUnleashEntityListener(this), this);
        pluginManager.registerEvents(new WeatherChangeListener(this), this);

        pluginManager.registerEvents(new JumpAndRunMoveListener(this), this);
        pluginManager.registerEvents(new BattleBoxMoveListener(this), this);

        getCommand("crash").setExecutor(new CrashCommand(this));
        getCommand("edit").setExecutor(new EditCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("setposition").setExecutor(new SetPositionCommand(this));
        getCommand("setupjnr").setExecutor(new SetupJnRCommand(this));
        getCommand("setuptopwall").setExecutor(new SetupTopWallCommand(this));
        getCommand("updateplayerwall").setExecutor(new UpdatePlayerWallCommand(this));

        new UpdateTopPlayersWallRunnable(this).runTaskTimer(this, 20, (20 * 60 * 10));
    }

    public void connectToServer(Player player, String serverName) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outStream);

        try {
            out.writeUTF("Connect");
            out.writeUTF(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(this, "BungeeCord", outStream.toByteArray());
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

    public String getSimpleTimeString(long seconds) {
        String time;

        if(seconds == 1) {
            time = "1 Sekunde";
        } else if(seconds < 60) {
            time = seconds + " Sekunden";
        } else if(seconds >= 60 && seconds < 120) {
            time = "1 Minute";
        }  else if(seconds >= (60 * 2) && seconds < (60 * 60)) {
            time = seconds / 60 + " Minuten";
        }  else if(seconds >= (60 * 60 * 1) && seconds < (60 * 60 * 2)) {
            time = seconds / 60 / 60 + " Stunde";
        }  else if(seconds >= (60 * 60 * 2) && seconds < (60 * 60 * 24)) {
            time = seconds / 60 / 60 + " Stunden";
        }  else if(seconds >= (60 * 60 * 24) && seconds < (60 * 60 * (24 * 2))) {
            time = seconds / 60 / 60 / 24 + " Tag";
        } else {
            time = seconds / 60 / 60 / 24 + " Tage";
        }

        return time;
    }

    public List<Player> getEditMode() {
        return this.editMode;
    }

    public List<Player> getFlyMode() {
        return this.flyMode;
    }

    public List<Player> getSetupJnRCooldown() {
        return this.setupJnRCooldown;
    }

    public List<Player> getInteractCooldown() {
        return this.interactCooldown;
    }

    public List<Player> getChallangerToggled() {
        return this.challangerToggled;
    }

    public List<Player> getSetupPlayerWallCooldown() {
        return this.setupPlayerWallCooldown;
    }

    public Map<Player, Integer> getSetupJnR() {
        return this.setupJnR;
    }

    public Map<Player, Integer> getSetupPlayerWall() {
        return this.setupPlayerWall;
    }

    public Map<Player, Player> getChallanger() {
        return this.challanger;
    }

    public Map<Player, Player> getChallangerInteracted() {
        return this.challangerInteracted;
    }

    public Map<Player, ChallangerGameType> getChallangerGameType() {
        return this.challangerGameType;
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

    public DatabaseDailyReward getDatabaseDailyReward() {
        return this.databaseDailyReward;
    }

    public LocationExecutor getLocationExecutor() {
        return this.locationExecutor;
    }

    public JumpAndRunGame getJumpAndRunGame() {
        return this.jumpAndRunGame;
    }

    public BattleBoxGame getBattleBoxGame() {
        return this.battleBoxGame;
    }

    public ItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public InventoryHandler getInventoryHandler() {
        return this.inventoryHandler;
    }

}
