package vip.marcel.gamestarbro.lobby.utils.challanger.games;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.concurrent.CompletableFuture;

public class HideAndSeekGame extends BukkitRunnable implements Listener {

    private final Lobby plugin;

    private final String prefix = "§8§l┃ §bHide'n'Seek §8► §7";

    private Player challanger;
    private Player challanged;

    private int warumUpSeconds;
    private int gameSeconds;

    private int rewardCoins;

    private boolean isInWarmUp;
    private boolean isFinished;

    public HideAndSeekGame(Lobby plugin, Player challanger, Player challanged) {
        this.plugin = plugin;
        this.challanger = challanger;
        this.challanged = challanged;

        this.warumUpSeconds = 10;
        this.gameSeconds = 60;

        this.rewardCoins = 20;

        this.isInWarmUp = false;
        this.isFinished = false;

        this.plugin.getIsInChallangeGame().add(challanger);
        this.plugin.getIsInChallangeGame().add(challanged);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void startGame() {
        this.isInWarmUp = true;

        this.challanger.sendMessage(this.prefix + "§e" + this.challanged.getName() + " §7hat nun Zeit sich zu verstecken.");
        this.challanged.sendMessage(this.prefix + "Du bist nun für §e" + this.challanger.getName() + " §7unsichtbar. Verstecke dich!");

        this.challanged.sendTitle("§bHide'n'Seek", "§7Verstecke dich", 10, 20, 10);

        this.challanger.playSound(this.challanger.getLocation(), Sound.ENTITY_WITCH_DRINK, 0.5F, 4.9F);
        this.challanged.playSound(this.challanged.getLocation(), Sound.ENTITY_WITCH_DRINK, 0.5F, 4.9F);

        this.challanger.hidePlayer(this.plugin, this.challanged);

        this.runTaskTimer(this.plugin, 20, 20);
    }

    public Player getOpponent(Player player) {
        if(this.challanger.equals(player)) {
            return this.challanged;
        } else {
            return this.challanger;
        }
    }

    @Override
    public void run() {

        if(this.challanger == null | this.challanged == null) {
            this.cancel();
        }

        if(this.isFinished) {
            this.cancel();
        }

        if(this.warumUpSeconds != -1) {

            if(this.warumUpSeconds > 0) {
                this.onWarmUpTick();
            }

            if(this.warumUpSeconds == 0) {
                this.isInWarmUp = false;
                this.onWarmUpEnd();
            }

            this.warumUpSeconds--;
        }

        if(this.warumUpSeconds == -1) {
            if(this.gameSeconds > 0) {
                this.onGameTick();
            } else {
                this.timeOutGame();
                this.cancel();
            }
            this.gameSeconds--;
        }

    }

    private void wonGame(Player winner) {
        final Player looser = getOpponent(winner);

        this.isFinished = true;

        CompletableFuture.runAsync(() -> {
            this.plugin.getDatabasePlayers().setCoins(winner.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(winner.getUniqueId()) + this.rewardCoins);
        }).thenAccept(unused -> {
            winner.sendTitle("§bHide'n'Seek", "§a+ §e" + this.rewardCoins + " Coins", 10, 15, 10);
        });

        this.plugin.getIsInChallangeGame().remove(challanger);
        this.plugin.getIsInChallangeGame().remove(challanged);

        looser.sendMessage(this.prefix + "Du hast gegen §e" + winner.getName() + " §7verloren.");
        winner.sendMessage(this.prefix + "Du hast gegen §e" + looser.getName() + " §7gewonnen.");

        looser.playSound(looser.getLocation(), Sound.ENTITY_DONKEY_DEATH, 0.5F, 4.9F);
        winner.playSound(winner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, 10F);
    }

    private void timeOutGame() {
        this.wonGame(this.challanged);
    }

    private void onWarmUpEnd() {
        this.isInWarmUp = false;
        this.challanger.showPlayer(this.plugin, this.challanged);

        this.challanger.sendMessage(this.prefix + "Du siehst §e" + this.challanged.getName() + " §7nun wieder.");
        this.challanged.sendMessage(this.prefix + "§aDu bist nun sichtbar..");

        this.challanger.playSound(this.challanger.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 0.5F, 4.9F);
        this.challanged.playSound(this.challanged.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 0.5F, 4.9F);
    }

    private void onGameTick() {
        if(this.gameSeconds == 10) {
            this.challanger.playSound(this.challanger.getLocation(), Sound.BLOCK_BARREL_OPEN, 0.5F, 4.9F);
            this.challanged.playSound(this.challanged.getLocation(), Sound.BLOCK_BARREL_OPEN, 0.5F, 4.9F);
        }

        this.challanger.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §7endet in §8» §e" + this.gameSeconds));
        this.challanged.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §7endet in §8» §e" + this.gameSeconds));
    }

    private void onWarmUpTick() {
        this.challanger.playSound(this.challanger.getLocation(), Sound.BLOCK_GLASS_STEP, 0.5F, 4.9F);
        this.challanged.playSound(this.challanged.getLocation(), Sound.BLOCK_GLASS_STEP, 0.5F, 4.9F);

        this.challanger.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §cWarmUp §8» §e" + this.warumUpSeconds));
        this.challanged.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §cWarmUp §8» §e" + this.warumUpSeconds));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        final Player looser = event.getPlayer();

        this.plugin.getIsInChallangeGame().remove(challanger);
        this.plugin.getIsInChallangeGame().remove(challanged);

        if(this.isFinished) {
            return;
        }

        final Player winner = getOpponent(looser);
        wonGame(winner);

    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        final Player winner = event.getPlayer();

        if(this.isFinished) {
            return;
        }

        if(this.isInWarmUp) {
            return;
        }

        if(event.getRightClicked() instanceof Player looser) {
            if(this.challanger.equals(winner) && this.challanged.equals(looser)) {
                wonGame(winner);
            }
        }

    }

}
