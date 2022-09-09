package vip.marcel.gamestarbro.lobby.utils.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import vip.marcel.gamestarbro.lobby.Lobby;

public class UpdateTopPlayersWallRunnable extends BukkitRunnable {

    private final Lobby plugin;

    public UpdateTopPlayersWallRunnable(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "updateplayerwall");
    }

}
