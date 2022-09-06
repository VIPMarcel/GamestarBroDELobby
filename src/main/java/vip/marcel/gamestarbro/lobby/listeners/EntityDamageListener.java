package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record EntityDamageListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player player) {
            event.setCancelled(true);
        }

    }

}
