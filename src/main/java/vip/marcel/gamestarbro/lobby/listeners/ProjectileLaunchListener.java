package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record ProjectileLaunchListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        event.setCancelled(true);
    }

}
