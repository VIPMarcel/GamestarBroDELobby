package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record BlockBurnListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent event) {
        event.setCancelled(true);
    }

}
