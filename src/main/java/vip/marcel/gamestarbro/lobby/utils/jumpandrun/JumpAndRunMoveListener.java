package vip.marcel.gamestarbro.lobby.utils.jumpandrun;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record JumpAndRunMoveListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        final JumpAndRunGame jumpAndRunGame = this.plugin.getJumpAndRunGame();

        try {
            if(player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.GOLD_BLOCK)) {
                if(!jumpAndRunGame.isJumping(player)) {
                    if(this.plugin.getFlyMode().contains(player)) {
                        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                        player.sendMessage(this.plugin.getPrefix() + "§eDu darfst nun nicht mehr fliegen.");
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        this.plugin.getFlyMode().remove(player);
                    }

                    this.plugin.getLocationConfiguration().reloadConfiguration();
                    jumpAndRunGame.startJumping(player);
                }
            }

            if(player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
                if(jumpAndRunGame.isJumping(player)) {
                    jumpAndRunGame.finishJumping(player);
                }
            }

            if(jumpAndRunGame.isJumping(player)) {
                if(player.getLocation().getBlockX() == jumpAndRunGame.getBlockLocations().get(jumpAndRunGame.getProcess(player)).getBlockX() &&
                        (player.getLocation().getBlockY() - 1) == jumpAndRunGame.getBlockLocations().get(jumpAndRunGame.getProcess(player)).getBlockY() &&
                        player.getLocation().getBlockZ() == jumpAndRunGame.getBlockLocations().get(jumpAndRunGame.getProcess(player)).getBlockZ()) {
                    jumpAndRunGame.nextJump(player);
                }

                if(player.getLocation().getBlockY() <= (jumpAndRunGame.getBlockLocations().get(jumpAndRunGame.getProcess(player)).getBlockY() - 10)) {
                    jumpAndRunGame.failJumping(player);
                }

                if(player.getLocation().getBlockY() >= (jumpAndRunGame.getBlockLocations().get(jumpAndRunGame.getProcess(player)).getBlockY() + 10)) {
                    jumpAndRunGame.failJumping(player);
                }
            }

        } catch(Exception ignore) {}

    }

}
