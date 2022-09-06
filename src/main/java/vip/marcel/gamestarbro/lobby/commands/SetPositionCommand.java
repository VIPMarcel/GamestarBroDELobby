package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record SetPositionCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.getPrefix() + "§cDieser Befehl darf nur von Spielern ausgeführt werden.");
            return true;
        }
        final Player player = (Player) sender;

        if(player.hasPermission("proxy.admin")) {

            if(arguments.length == 0) {
                player.sendMessage(this.plugin.getPrefix() + "§7Du musst einen §eNamen §7angeben, um die Position zu setzten.");
                return true;
            }
            String locationName = arguments[0];

            if(arguments.length > 1) {
                player.sendMessage(this.plugin.getPrefix() + this.plugin.getUnknownCommand());
                return true;
            }

            if(this.plugin.getLocationExecutor().doesLocationExists(locationName)) {
                this.plugin.getLocationExecutor().saveLocation(locationName, player.getLocation());
                player.sendMessage(this.plugin.getPrefix() + "§eDu hast die Position für §6" + locationName + " §eüberschrieben.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
            }
            else {
                this.plugin.getLocationExecutor().saveLocation(locationName, player.getLocation());
                player.sendMessage(this.plugin.getPrefix() + "§eDu hast die Position für §6" + locationName + " §egesetzt.");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
            }

        }
        else {
            player.sendMessage(this.plugin.getPrefix() + this.plugin.getNoPermissions());
        }

        return true;
    }

}
