package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record EntityDamageByEntityListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        if(event.getDamager() instanceof Player player) {

            if(!(this.plugin.getEditMode().contains(player) && player.getGameMode().equals(GameMode.CREATIVE))) {

                if(event.getEntity() instanceof Player target) {

                    if(!(this.plugin.getBattleBoxGame().isInBox(player) && this.plugin.getBattleBoxGame().isInBox(target))) {
                        event.setCancelled(true);
                    }

                } else {
                    event.setCancelled(true);
                }

            }

        } else {
            event.setCancelled(true);
        }

    }
}
