package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record BlockPhysicsListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

}
