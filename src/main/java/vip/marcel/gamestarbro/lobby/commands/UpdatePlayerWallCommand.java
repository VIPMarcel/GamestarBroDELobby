package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.List;
import java.util.UUID;

public record UpdatePlayerWallCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(sender.hasPermission("proxy.admin")) {

            final List<UUID> topCoinPlayers = this.plugin.getDatabasePlayers().getTopCoinPlayers(3);

            for(int i = 0; i != topCoinPlayers.size(); i++) {

                if(this.plugin.getLocationExecutor().doesLocationExists("TopPlayers." + i)) {
                    final Location signLocation = this.plugin.getLocationExecutor().getLocation("TopPlayers." + i);

                    if(signLocation.getBlock().getState() instanceof final Sign sign) {

                        sign.setLine(0, "§bPlatz #" + i);
                        sign.setLine(1, "§8§m------------");
                        sign.setLine(2, "§a" + this.plugin.getDatabasePlayers().getPlayerName(topCoinPlayers.get(i - 1)));
                        sign.setLine(3, "§e" + this.plugin.getDatabasePlayers().getCoins(topCoinPlayers.get(i - 1)) + " Coins");
                        sign.update(true);
                    }

                    final Location skullLocation = signLocation.add(0, 1, 0).clone();

                    if(skullLocation.getBlock().getState() instanceof final Skull skull) {

                        //skull.setType(Material.PLAYER_WALL_HEAD);
                        skull.setOwner(this.plugin.getDatabasePlayers().getPlayerName(topCoinPlayers.get(i - 1)));
                        skull.update(true);
                    }

                }

            }

            if(sender instanceof Player player) {
                player.sendMessage(this.plugin.getPrefix() + "Die §eTop-#3 Spieler Wand §7wurde neu geladen.");
            }

        } else {
            sender.sendMessage(this.plugin.getPrefix() + this.plugin.getNoPermissions());
        }

        return true;
    }

}
