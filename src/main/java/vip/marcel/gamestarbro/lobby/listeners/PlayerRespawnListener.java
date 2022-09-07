package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerRespawnListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        player.setMaxHealth(4);
        player.setHealth(4);
        this.plugin.getItemHandler().giveItemsToPlayer(player);

    }

}
