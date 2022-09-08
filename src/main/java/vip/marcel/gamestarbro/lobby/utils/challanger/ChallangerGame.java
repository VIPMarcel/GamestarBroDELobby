package vip.marcel.gamestarbro.lobby.utils.challanger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import vip.marcel.gamestarbro.lobby.Lobby;

public abstract class ChallangerGame extends BukkitRunnable implements Listener {

    private final Lobby plugin;
    private final String challangerPrefix;

    private final Game game;

    private boolean gameReady;
    private boolean isInWarmUp;

    public ChallangerGame(Lobby plugin, Player challanger, Player challanged) {
        this.plugin = plugin;
        this.challangerPrefix = "§8§l┃ §bChallanger §8► §7";

        this.game = new Game();
        this.game.setChallanger(challanger);
        this.game.setChallanged(challanged);

        this.gameReady = false;
        this.isInWarmUp = true;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.runTaskTimer(plugin, 20, 20);

        this.setupGame();
    }

    @Override
    public void run() {

        if(!this.gameReady) {
            return;
        }

        if(this.game.getChallanger() == null | this.game.getChallanged() == null) {
            this.cancel();
        }

        if(this.game.isFinished()) {
            this.cancel();
        }

        if(this.game.getWarmUpSeconds() != -1) {

            if(this.game.getWarmUpSeconds() > 0) {
                this.onWarmUpTick();
            }

            if(this.game.getWarmUpSeconds() == 0) {
                this.isInWarmUp = false;
                this.onWarmUpEnd();
            }

            this.game.setWarmUpSeconds(this.game.getWarmUpSeconds() - 1);
        }

        if(this.game.getWarmUpSeconds() == -1) {
            if(this.game.getGameSeconds() > 0) {
                this.onGameTick();
            } else {
                this.timeOutGame();
                this.cancel();
            }
            this.game.setGameSeconds(this.game.getGameSeconds() - 1);
        }

    }

    public abstract void setupGame();

    public abstract void onGameStart();

    public abstract void timeOutGame();
    public abstract void wonGame(Player winner);

    public abstract void onWarmUpTick();
    public abstract void onGameTick();

    public abstract void onWarmUpEnd();

    public Player getOpponent(Player player) {
        if(this.game.getChallanger().equals(player)) {
            return this.game.getChallanged();
        } else {
            return this.game.getChallanger();
        }
    }

    public void start() {
        this.game.setFinished(false);
        this.gameReady = true;
        this.onGameStart();
    }

    public Lobby getPlugin() {
        return this.plugin;
    }

    public Game getGame() {
        return this.game;
    }

    public boolean isGameReady() {
        return this.gameReady;
    }

    public boolean isInWarmUp() {
        return this.isInWarmUp;
    }

}
