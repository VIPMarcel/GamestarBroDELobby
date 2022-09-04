package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public class PlayerQuitListener implements Listener {

    private final Lobby plugin;

    public PlayerQuitListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {

        event.setQuitMessage(null);

    }

}
