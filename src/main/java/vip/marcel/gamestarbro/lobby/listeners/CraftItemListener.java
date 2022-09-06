package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record CraftItemListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent event) {

        if(event.getWhoClicked() instanceof Player player) {
            if(!(this.plugin.getEditMode().contains(player) && player.getGameMode().equals(GameMode.CREATIVE))) {
                event.setCancelled(true);
            }
        }

    }

}
