package vip.marcel.gamestarbro.lobby.utils.battlebox;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record BattleBoxMoveListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if(this.plugin.getBattleBoxGame().isInBox(player)) {
            this.plugin.getBattleBoxGame().enterBox(player);
        } else {
            this.plugin.getBattleBoxGame().leaveBox(player);
        }

    }

}
