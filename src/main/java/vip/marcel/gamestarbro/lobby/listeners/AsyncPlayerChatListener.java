package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public class AsyncPlayerChatListener implements Listener {

    private final Lobby plugin;

    public AsyncPlayerChatListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

        event.setFormat("§8§l┃ §7" + event.getPlayer().getDisplayName() + " §8» §f" + event.getMessage());

    }

}
