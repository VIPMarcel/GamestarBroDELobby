package vip.marcel.gamestarbro.lobby.utils.challanger.games;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import vip.marcel.gamestarbro.lobby.Lobby;
import vip.marcel.gamestarbro.lobby.utils.challanger.ChallangerGame;

import java.util.concurrent.CompletableFuture;

public class HideAndSeekGame extends ChallangerGame {

    public HideAndSeekGame(Lobby plugin, Player challanger, Player challanged) {
        super(plugin, challanger, challanged);
    }

    @Override
    public void setupGame() {
        this.getGame().setName("Hide'n'Seek");
        this.getGame().setDescription("Finde deinen Gegener in der Lobby.");
        this.getGame().setGamePrefix("§8§l┃ §bHide'n'Seek §8► §7");

        this.getGame().setWarmUpSeconds(10);
        this.getGame().setGameSeconds(60);

        this.getGame().setRewardCoins(20);
    }

    @Override
    public void onGameStart() {
        this.getGame().getChallanger().sendMessage(this.getGame().getGamePrefix() + "§e" + this.getGame().getChallanged().getName() + " §7hat nun Zeit sich zu verstecken.");
        this.getGame().getChallanged().sendMessage(this.getGame().getGamePrefix() + "Du bist nun für §e" + this.getGame().getChallanger().getName() + " §7unsichtbar. Verstecke dich!");

        this.getGame().getChallanged().sendTitle("§bHide'n'Seek", "§7Verstecke dich", 10, 20, 10);

        this.getGame().getChallanger().playSound(this.getGame().getChallanger().getLocation(), Sound.ENTITY_WITCH_DRINK, 0.5F, 4.9F);
        this.getGame().getChallanged().playSound(this.getGame().getChallanged().getLocation(), Sound.ENTITY_WITCH_DRINK, 0.5F, 4.9F);

        this.getGame().getChallanger().hidePlayer(this.getPlugin(), this.getGame().getChallanged());
    }

    @Override
    public void timeOutGame() {
        this.wonGame(this.getGame().getChallanged());
    }

    @Override
    public void wonGame(Player winner) {
        final Player looser = getOpponent(winner);

        this.getGame().setFinished(true);

        CompletableFuture.runAsync(() -> {
            getPlugin().getDatabasePlayers().setCoins(winner.getUniqueId(), getPlugin().getDatabasePlayers().getCoins(winner.getUniqueId()) + this.getGame().getRewardCoins());
        }).thenAccept(unused -> {
            winner.sendTitle("§b" + this.getGame().getName(), "§a+ §e" + this.getGame().getRewardCoins() + " Coins", 10, 15, 10);
        });

        looser.sendMessage(this.getGame().getGamePrefix() + "Du hast gegen §e" + winner.getName() + " §7verloren.");
        winner.sendMessage(this.getGame().getGamePrefix() + "Du hast gegen §e" + looser.getName() + " §7gewonnen.");

        looser.playSound(looser.getLocation(), Sound.ENTITY_DONKEY_DEATH, 0.5F, 4.9F);
        winner.playSound(winner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, 10F);
    }

    @Override
    public void onWarmUpTick() {
        this.getGame().getChallanger().playSound(this.getGame().getChallanger().getLocation(), Sound.BLOCK_GLASS_STEP, 0.5F, 4.9F);
        this.getGame().getChallanged().playSound(this.getGame().getChallanged().getLocation(), Sound.BLOCK_GLASS_STEP, 0.5F, 4.9F);

        this.getGame().getChallanger().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §cWarmUp §8» §e" + this.getGame().getWarmUpSeconds()));
        this.getGame().getChallanged().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §cWarmUp §8» §e" + this.getGame().getWarmUpSeconds()));
    }

    @Override
    public void onGameTick() {

        if(this.getGame().getGameSeconds() == 10) {
            this.getGame().getChallanger().playSound(this.getGame().getChallanger().getLocation(), Sound.BLOCK_BARREL_OPEN, 0.5F, 4.9F);
            this.getGame().getChallanged().playSound(this.getGame().getChallanged().getLocation(), Sound.BLOCK_BARREL_OPEN, 0.5F, 4.9F);
        }

        this.getGame().getChallanger().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §7endet in §8» §e" + this.getGame().getGameSeconds()));
        this.getGame().getChallanged().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§bHide'n'Seek §7endet in §8» §e" + this.getGame().getGameSeconds()));

    }

    @Override
    public void onWarmUpEnd() {
        this.getGame().getChallanger().showPlayer(getPlugin(), this.getGame().getChallanged());

        this.getGame().getChallanger().sendMessage(this.getGame().getGamePrefix() + "Du siehst §e" + this.getGame().getChallanged().getName() + " §7nun wieder.");
        this.getGame().getChallanged().sendMessage(this.getGame().getGamePrefix() + "§aDu bist nun sichtbar..");

        this.getGame().getChallanger().playSound(this.getGame().getChallanger().getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 0.5F, 4.9F);
        this.getGame().getChallanged().playSound(this.getGame().getChallanged().getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 0.5F, 4.9F);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        final Player looser = event.getPlayer();

        if(this.getGame().isFinished()) {
            return;
        }

        final Player winner = getOpponent(looser);
        wonGame(winner);

    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        final Player winner = event.getPlayer();

        if(this.getGame().isFinished()) {
            return;
        }

        if(this.isInWarmUp()) {
            return;
        }

        if(event.getRightClicked() instanceof Player looser) {
            if(this.getGame().getChallanger().equals(winner) && this.getGame().getChallanged().equals(looser)) {
                wonGame(winner);
            }
        }

    }

}
