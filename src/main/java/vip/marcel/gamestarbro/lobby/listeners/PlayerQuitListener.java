package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerQuitListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        this.plugin.getEditMode().remove(player);
        this.plugin.getFlyMode().remove(player);
        this.plugin.getChallanger().remove(player);
        this.plugin.getChallangerInteracted().remove(player);
        this.plugin.getChallangerGameType().remove(player);

        this.plugin.getSetupJnR().remove(player);
        this.plugin.getSetupPlayerWall().remove(player);

        if(this.plugin.getJumpAndRunGame().isJumping(player)) {
            this.plugin.getJumpAndRunGame().failJumping(player);
        }

        this.plugin.getBattleBoxGame().leaveBox(player);

    }

}
