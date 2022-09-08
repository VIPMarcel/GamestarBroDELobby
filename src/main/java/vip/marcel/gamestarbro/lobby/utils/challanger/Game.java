package vip.marcel.gamestarbro.lobby.utils.challanger;

import org.bukkit.entity.Player;

public class Game {

    private String name;
    private String description;
    private String gamePrefix;

    private int warmUpSeconds;
    private int gameSeconds;

    private boolean finished;

    private int rewardCoins;

    private Player challanger;
    private Player challanged;

    public Game() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGamePrefix() {
        return this.gamePrefix;
    }

    public void setGamePrefix(String gamePrefix) {
        this.gamePrefix = gamePrefix;
    }

    public int getWarmUpSeconds() {
        return this.warmUpSeconds;
    }

    public void setWarmUpSeconds(int warmUpSeconds) {
        this.warmUpSeconds = warmUpSeconds;
    }

    public int getGameSeconds() {
        return this.gameSeconds;
    }

    public void setGameSeconds(int gameSeconds) {
        this.gameSeconds = gameSeconds;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getRewardCoins() {
        return this.rewardCoins;
    }

    public void setRewardCoins(int rewardCoins) {
        this.rewardCoins = rewardCoins;
    }

    public Player getChallanger() {
        return this.challanger;
    }

    public void setChallanger(Player challanger) {
        this.challanger = challanger;
    }

    public Player getChallanged() {
        return this.challanged;
    }

    public void setChallanged(Player challanged) {
        this.challanged = challanged;
    }

}
