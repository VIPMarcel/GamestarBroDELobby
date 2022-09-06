package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record WeatherChangeListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
