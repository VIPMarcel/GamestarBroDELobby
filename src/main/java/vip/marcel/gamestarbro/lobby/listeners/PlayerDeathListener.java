package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.concurrent.CompletableFuture;

public record PlayerDeathListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final Player killer = event.getEntity().getKiller();

        event.setDeathMessage(null);
        event.setKeepLevel(true);
        event.getDrops().clear();
        event.setDroppedExp(0);

        if(this.plugin.getBattleBoxGame().isInBox(player)) {
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                player.spigot().respawn();
                player.teleport(this.plugin.getLocationExecutor().getLocation("BattleBoxRespawn"));
            }, 5);

            if(killer == null) {
                player.sendMessage(this.plugin.getBattleBoxGame().getPrefix() + "Du bist gestorben.");
                return;
            }

            killer.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 10F, 10F);

            this.plugin.getBattleBoxGame().addKill(killer);
            this.plugin.getBattleBoxGame().announceKillStreak(killer);

            killer.setHealth(20);

            CompletableFuture.runAsync(() -> {
               this.plugin.getDatabasePlayers().setCoins(killer.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(killer.getUniqueId()) + 5);
            });

            player.sendMessage(this.plugin.getBattleBoxGame().getPrefix() + "Du wurdest von §e" + killer.getName() + " §7getötet.");
            killer.sendMessage(this.plugin.getBattleBoxGame().getPrefix() + "Du hast §e" + player.getName() + " §7getötet. §8(§a+ §e5 Coins§8)");
        }

    }

}
