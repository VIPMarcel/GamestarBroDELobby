package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerJoinListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        event.setJoinMessage(null);

        player.setOp(false);
        player.setMaxHealth(4);
        player.setHealth(4);
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        player.setExp(0);
        player.spigot().setCollidesWithEntities(false);
        player.setGlowing(false);
        player.setCollidable(false);
        player.setFireTicks(0);
        player.setFreezeTicks(0);

        if(this.plugin.getLocationExecutor().doesLocationExists("Spawn")) {
            player.teleport(this.plugin.getLocationExecutor().getLocation("Spawn"));
        } else {
            player.sendMessage(this.plugin.getPrefix() + "§cDer Spawn wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
        }

        player.sendTitle("§8▻ §6§nGamestarBro.de§r §8◅", this.plugin.getColorFlowMessageBuilder()
                .buildColorFlowMessage("Herzlich Willkommen!", "#CC6600", "#FFFF66"));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);

        if(player.hasPermission("proxy.staff")) {
            this.plugin.getServer().getWorld(player.getWorld().getName()).strikeLightningEffect(player.getLocation());
        }

        this.plugin.getItemHandler().giveItemsToPlayer(player);
    }

}
