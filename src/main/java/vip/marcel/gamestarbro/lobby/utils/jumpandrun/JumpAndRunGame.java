package vip.marcel.gamestarbro.lobby.utils.jumpandrun;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class JumpAndRunGame implements JumpAndRun {

    private final Lobby plugin;

    private final String prefix = "§8§l┃ §6Jump'n'Run §8► §7";

    private final Map<Player, Integer> jumpCount;
    private final Map<Player, Long> jumpStarted;
    private final Map<Player, BukkitRunnable> jumpRunnable;

    public JumpAndRunGame(Lobby plugin) {
        this.plugin = plugin;

        this.jumpCount = Maps.newHashMap();
        this.jumpStarted = Maps.newHashMap();
        this.jumpRunnable = Maps.newHashMap();
    }

    @Override
    public boolean isJumping(Player player) {
        return this.jumpCount.containsKey(player);
    }

    @Override
    public void startJumping(Player player) {
        applyProcess(player);
        this.jumpStarted.put(player, System.currentTimeMillis());

        this.jumpRunnable.put(player, new BukkitRunnable() {
            @Override
            public void run() {
                final Long startJumpMillis = JumpAndRunGame.this.jumpStarted.get(player);
                final long needTimeMillis = System.currentTimeMillis() - startJumpMillis;
                final long needTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(needTimeMillis);

                long hours = needTimeSeconds / 3600;
                long minutes = (needTimeSeconds % 3600) / 60;
                long seconds = needTimeSeconds % 60;

                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§aJump'n'Run Zeit §8» §e" + timeString));
            }
        });
        this.jumpRunnable.get(player).runTaskTimer(this.plugin, 20, 20);

        setNextBlock(player);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.5F);
    }

    @Override
    public void finishJumping(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0.5F);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.5F);
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 0.5F, 0.5F);
        Bukkit.getWorld(player.getWorld().getName()).spawnParticle(Particle.TOTEM, player.getLocation(), 1);

        try {
            removeLastBlock(player);
            removeCurrentBlock(player);
            removeNextBlock(player);
        } catch (IndexOutOfBoundsException ignore) {}

        final long startJumpMillis = this.jumpStarted.get(player);
        final long needTimeMillis = System.currentTimeMillis() - startJumpMillis;

        final long seconds = TimeUnit.MILLISECONDS.toSeconds(needTimeMillis);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(needTimeMillis);

        if(seconds < 60) {
            player.sendMessage(this.prefix + "§aErfolgreich beendet nach §e" + seconds + " Sekunden§a.");
        } else if(seconds == 60) {
            player.sendMessage(this.prefix + "§aErfolgreich beendet nach §e" + minutes + " Minute§a.");
        } else {
            player.sendMessage(this.prefix + "§aErfolgreich beendet nach §e" + minutes + " Minuten§a.");
        }

        if(minutes >= 1) {
            CompletableFuture.runAsync(() -> {
                this.plugin.getDatabasePlayers().setCoins(player.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(player.getUniqueId()) + 500);
            }).thenAccept(unused -> {
                player.sendMessage(this.prefix + "Du hast §e500 Coins §7erhalten.");
            });
        } else {
            Bukkit.broadcastMessage(this.prefix + "§e" + player.getName() + " §cbenutzt §eClientmodifikationen§c.");
        }

        this.jumpCount.remove(player);
        this.jumpRunnable.get(player).cancel();
        this.jumpRunnable.remove(player);
        this.jumpStarted.remove(player);
    }

    @Override
    public void failJumping(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 0.5F, 0.5F);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
        player.playSound(player.getLocation(), Sound.BLOCK_BASALT_FALL, 0.5F, 0.5F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 3));

        try {
            if(getProcess(player) >= 2) {
                removeLastBlock(player);
            }
            if(getProcess(player) >= 1) {
                removeCurrentBlock(player);
            }
            removeNextBlock(player);
        } catch (IndexOutOfBoundsException ignore) {}

        if(getProcess(player) >= 3) {
            int coins = 2 + getProcess(player);
            CompletableFuture.runAsync(() -> {
                this.plugin.getDatabasePlayers().setCoins(player.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(player.getUniqueId()) + coins);
            }).thenAccept(unused -> {
                player.sendMessage(this.prefix + "Du hast §e" + coins + " Coins §7erhalten.");
            });
        }

        this.jumpCount.remove(player);
        this.jumpRunnable.get(player).cancel();
        this.jumpRunnable.remove(player);
        this.jumpStarted.remove(player);
    }

    @Override
    public void nextJump(Player player) {
        applyProcess(player);

        setNextBlock(player);

        if(getProcess(player) != 0) {
            removeLastBlock(player);
        }

    }

    @Override
    public int getProcess(Player player) {
        return this.jumpCount.get(player);
    }

    @Override
    public void applyProcess(Player player) {
        if(!this.jumpCount.containsKey(player)) {
            this.jumpCount.put(player, 0);
        } else {
            this.jumpCount.put(player, getProcess(player) + 1);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.25F, 0.25F);
    }

    @Override
    public List<Location> getBlockLocations() {
        final LinkedList<Location> output = Lists.newLinkedList();

        for(String number : this.plugin.getLocationConfiguration().getConfiguration().getConfigurationSection("Locations.JumpNRun").getKeys(false)) {
            output.addLast(this.plugin.getLocationExecutor().getLocation("JumpNRun." + number));
        }

        return output;
    }

    @Override
    public void setNextBlock(Player player) {
        // 0 | start

        final Location nextLocation = getBlockLocations().get(getProcess(player));

        Bukkit.getWorld(nextLocation.getWorld().getName()).setType(nextLocation, Material.SPRUCE_PLANKS);
        Bukkit.getWorld(nextLocation.getWorld().getName()).spawnParticle(Particle.FLASH, nextLocation, 1);
    }

    @Override
    public void removeLastBlock(Player player) {
        final AtomicBoolean inUse = new AtomicBoolean(false);

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            if(players != player) {
                if(isJumping(players)) {
                    final int process = getProcess(players);

                    if((getProcess(player) - 2) == process) {
                        inUse.set(true);
                    }

                }
            }
        });

        if(!inUse.get()) {
            final Location lastLocation = getBlockLocations().get(getProcess(player) - 2);

            if(Bukkit.getWorld(lastLocation.getWorld().getName()).getBlockAt(lastLocation).getType().equals(Material.SPRUCE_PLANKS)) {
                Bukkit.getWorld(lastLocation.getWorld().getName()).setType(lastLocation, Material.AIR);
            }
        }

    }

    @Override
    public void removeCurrentBlock(Player player) {
        final AtomicBoolean inUse = new AtomicBoolean(false);

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            if(players != player) {
                if(isJumping(players)) {
                    final int process = getProcess(players);

                    if((getProcess(player) - 1) == process) {
                        inUse.set(true);
                    }

                }
            }
        });

        if(!inUse.get()) {
            final Location currentLocation = getBlockLocations().get(getProcess(player) - 1);

            if(Bukkit.getWorld(currentLocation.getWorld().getName()).getBlockAt(currentLocation).getType().equals(Material.SPRUCE_PLANKS)) {
                Bukkit.getWorld(currentLocation.getWorld().getName()).setType(currentLocation, Material.AIR);
            }
        }

    }

    @Override
    public void removeNextBlock(Player player) {
        final AtomicBoolean inUse = new AtomicBoolean(false);

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            if(players != player) {
                if(isJumping(players)) {
                    final int process = getProcess(players);

                    if(getProcess(player) == process) {
                        inUse.set(true);
                    }

                }
            }
        });

        if(!inUse.get()) {
            final Location nextLocation = getBlockLocations().get(getProcess(player));

            if(Bukkit.getWorld(nextLocation.getWorld().getName()).getBlockAt(nextLocation).getType().equals(Material.SPRUCE_PLANKS)) {
                Bukkit.getWorld(nextLocation.getWorld().getName()).setType(nextLocation, Material.AIR);
            }
        }

    }

}
