package vip.marcel.gamestarbro.lobby.utils.jumpandrun;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface JumpAndRun {

    boolean isJumping(Player player);

    void startJumping(Player player);

    void finishJumping(Player player);

    void failJumping(Player player);

    void nextJump(Player player);

    int getProcess(Player player);

    void applyProcess(Player player);

    List<Location> getBlockLocations();

    void setNextBlock(Player player);

    void removeLastBlock(Player player);

    void removeCurrentBlock(Player player);

    void removeNextBlock(Player player);

}
