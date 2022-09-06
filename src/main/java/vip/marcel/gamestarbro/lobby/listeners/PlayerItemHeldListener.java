package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerItemHeldListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 0.75F);
    }

}
