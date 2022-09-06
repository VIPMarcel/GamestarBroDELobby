package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record FoodLevelChangeListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
