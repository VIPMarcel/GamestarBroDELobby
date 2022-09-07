package vip.marcel.gamestarbro.lobby.utils.battlebox;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.List;
import java.util.Map;

public class BattleBoxGame {

    private final Lobby plugin;

    final String prefix = "§8§l┃ §6BattleBox §8► §7";

    private final List<Player> inBox;
    private final Map<Player, Integer> killStreak;
    private final Map<Player, BukkitRunnable> gameRunnable;

    public BattleBoxGame(Lobby plugin) {
        this.plugin = plugin;

        this.inBox = Lists.newArrayList();
        this.killStreak = Maps.newHashMap();
        this.gameRunnable = Maps.newHashMap();
    }

    public boolean isInBox(Player player) {
        return this.inCuboid(player.getLocation(),
                this.plugin.getLocationExecutor().getLocation("BattleBox1"),
                this.plugin.getLocationExecutor().getLocation("BattleBox2"));
    }

    public void enterBox(Player player) {

        if(this.inBox.contains(player)) {
            return;
        }

        player.setMaxHealth(20);
        player.setHealth(20);

        player.getInventory().clear();
        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 0.5F, 0.5F);

        player.getInventory().setItem(0, this.plugin.item(Material.STONE_SWORD).setDisplayname("§8» §cBattle Schwert").build());
        this.inBox.add(player);
        this.killStreak.put(player, 0);
        this.gameRunnable.put(player, new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§aBattleBox KillStreak §8» §e" + killStreak.get(player)));
            }
        });
        this.gameRunnable.get(player).runTaskTimer(this.plugin, 20, 20);
    }

    public void leaveBox(Player player) {

        if(!this.inBox.contains(player)) {
            return;
        }

        player.setMaxHealth(4);
        player.setHealth(4);

        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 0.5F, 0.5F);
        this.inBox.remove(player);
        this.killStreak.remove(player);
        this.gameRunnable.get(player).cancel();
        this.gameRunnable.remove(player);
        this.plugin.getItemHandler().giveItemsToPlayer(player);
    }

    public void announceKillStreak(Player player) {
        final Integer kills = this.killStreak.get(player);

        if(kills == 3 | kills == 5 | kills == 10 | kills == 15 | kills == 20 | kills == 25 | kills == 30 | kills == 35 | kills == 40 | kills == 45 | kills == 50) {
            Bukkit.broadcastMessage(this.prefix + "Der Spieler §e" + player.getName() + " §7hat eine Killstreak von §e" + kills + "§7!");

            Bukkit.getOnlinePlayers().forEach(players -> {
                players.playSound(players.getLocation(), Sound.BLOCK_BELL_USE, 10F, 10F);
            });

        }

    }

    public void addKill(Player player) {
        this.killStreak.put(player, this.killStreak.get(player) + 1);
    }

    private boolean inCuboid(Location origin, Location location1, Location location2){
        return new IntRange(location1.getX(), location2.getX()).containsDouble(origin.getX())
                && new IntRange(location1.getY(), location2.getY()).containsDouble(origin.getY())
                &&  new IntRange(location1.getZ(), location2.getZ()).containsDouble(origin.getZ());
    }

    public String getPrefix() {
        return this.prefix;
    }

}
