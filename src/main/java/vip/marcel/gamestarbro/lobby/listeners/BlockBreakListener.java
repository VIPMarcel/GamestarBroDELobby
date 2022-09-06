package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record BlockBreakListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if(!(this.plugin.getEditMode().contains(player) && player.getGameMode().equals(GameMode.CREATIVE))) {
            event.setCancelled(true);
        }

    }

}
