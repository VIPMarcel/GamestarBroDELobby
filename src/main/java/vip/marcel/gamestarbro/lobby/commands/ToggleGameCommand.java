package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record ToggleGameCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(sender.hasPermission("proxy.admin")) {

            if(arguments.length == 0) {
                sender.sendMessage(this.plugin.getGlobalPrefix() + "Gebe einen §eSpielmodus §7an, um ihn zu sperren oder freizugeben.");
                sender.sendMessage(this.plugin.getGlobalPrefix() + "§8» §eSurvival");
                return true;
            }

            if(arguments[0].equalsIgnoreCase("survival")) {

                if(this.plugin.getToggledGames().contains("survival")) {
                    this.plugin.getToggledGames().remove("survival");
                    sender.sendMessage(this.plugin.getGlobalPrefix() + "§aDu hast §eSurvival §aaus den Wartungsarbeiten gesetzt.");
                } else {
                    this.plugin.getToggledGames().add("survival");
                    sender.sendMessage(this.plugin.getGlobalPrefix() + "§cDu hast §eSurvival §cin Wartungsarbeiten gesetzt.");
                }

            } else {
                sender.sendMessage(this.plugin.getGlobalPrefix() + "Gebe einen §eSpielmodus §7an, um ihn zu sperren oder freizugeben.");
                sender.sendMessage(this.plugin.getGlobalPrefix() + "§8» §eSurvival");
                return true;
            }

        } else {
            sender.sendMessage(this.plugin.getGlobalPrefix() + this.plugin.getNoPermissions());
        }

        return true;
    }

}
