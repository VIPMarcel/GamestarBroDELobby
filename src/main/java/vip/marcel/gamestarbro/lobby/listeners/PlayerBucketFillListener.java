package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerBucketFillListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerBucketFillEvtn(PlayerBucketFillEvent event) {
        final Player player = event.getPlayer();

        if(!this.plugin.getEditMode().contains(player)) {
            event.setCancelled(true);
        }

    }

}
