package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, 10F);

        if(player.hasPermission("proxy.staff")) {
            this.plugin.getServer().getWorld(player.getWorld().getName()).strikeLightningEffect(player.getLocation());
        }

        if(this.plugin.getLocationExecutor().doesLocationExists("SpawnHologram")) {
            this.plugin.hologram(this.plugin.getLocationExecutor().getLocation("SpawnHologram"),
                            "§8§m--------------------------------",
                            "§7Herzlich Willkommen auf,",
                            "§6GamestarBro.de",
                            " ",
                            "§aWir haben unseren Server vergrößert.",
                            "§8» §7Bei §eFehlern §7oder §eFragen",
                            "§7wende dich an unsere §cTeammitglieder§7.",
                            " ",
                            "§7Schau dich doch mal ein wenig um,",
                            "§7es gibt vieles zu §bentdecken§7.",
                            "§8§m--------------------------------")
                    .showHologramForPlayer(player);
        }

        if(this.plugin.getLocationExecutor().doesLocationExists("StartJumpAndRun")) {
            this.plugin.hologram(this.plugin.getLocationExecutor().getLocation("StartJumpAndRun"),
                            "§8§l┃ §aStart §8§l┃")
                    .showHologramForPlayer(player);
        }

        if(this.plugin.getLocationExecutor().doesLocationExists("ZielJumpAndRun")) {
            this.plugin.hologram(this.plugin.getLocationExecutor().getLocation("ZielJumpAndRun"),
                            "§8§l┃ §aZiel §8§l┃")
                    .showHologramForPlayer(player);
        }

        if(this.plugin.getLocationExecutor().doesLocationExists("BattleBoxHologram")) {
            this.plugin.hologram(this.plugin.getLocationExecutor().getLocation("BattleBoxHologram"),
                            "§8§l┃ §cBattle-Box §8§l┃")
                    .showHologramForPlayer(player);
        }

        if(this.plugin.getLocationExecutor().doesLocationExists("TopPlayersHologram")) {
            this.plugin.hologram(this.plugin.getLocationExecutor().getLocation("TopPlayersHologram"),
                            "§8§l┃ §aTOP §e#3 §7Spieler §8§l┃")
                    .showHologramForPlayer(player);
        }

        final AtomicLong timeStamp = new AtomicLong();
        final AtomicInteger playerStreak = new AtomicInteger(0);
        final AtomicBoolean alreadyCollected = new AtomicBoolean(true);

        CompletableFuture.runAsync(() -> {
            this.plugin.getDatabaseDailyReward().createPlayer(player.getUniqueId());
            timeStamp.set(this.plugin.getDatabaseDailyReward().getTimeStamp(player.getUniqueId()));
            playerStreak.set(this.plugin.getDatabasePlayers().getLoginStreak(player.getUniqueId()));
            alreadyCollected.set(this.plugin.getDatabasePlayers().getLoginStreakCollected(player.getUniqueId()));
        }).thenAccept(unused -> {

            if(!alreadyCollected.get()) {
                if(playerStreak.get() == 3 |
                        playerStreak.get() == 5 |
                        playerStreak.get() == 7 |
                        playerStreak.get() == 10 |
                        playerStreak.get() == 14 |
                        playerStreak.get() == 21 |
                        playerStreak.get() == 30) {
                    player.sendMessage(this.plugin.getGlobalPrefix() + "§aDu kannst deine §eLogin- Streak §aBelohnung abholen.");
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5F, 0.5F);
                }
            }

            if(timeStamp.get() == -1 | timeStamp.get() <= System.currentTimeMillis()) {
                player.sendMessage(this.plugin.getGlobalPrefix() + "§aDu kannst deine §etägliche Belohnung §aabholen.");
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5F, 0.5F);
            }

        });

        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100D);

        this.plugin.getItemHandler().giveItemsToPlayer(player);
    }

}
