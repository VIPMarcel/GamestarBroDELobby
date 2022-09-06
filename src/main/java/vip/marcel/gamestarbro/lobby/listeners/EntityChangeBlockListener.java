package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record EntityChangeBlockListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onEntityChangeBlockEVent(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

}
