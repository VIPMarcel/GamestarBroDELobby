package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record SetupJnRCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(sender instanceof Player player) {
            if(player.hasPermission("proxy.admin")) {

                if(!this.plugin.getEditMode().contains(player)) {
                    player.performCommand("edit");
                }

                player.getInventory().addItem(this.plugin.item(Material.SLIME_BALL)
                        .setDisplayname("§8» §7JnR §8| §aBlöcke auswählen")
                        .setLore("§8► §7Klicke in der richtigen Reihenfolge auf die Jump and Run- Blöcke,",
                                "   §7um diese für das Jump and Run zu speichern.")
                        .build());

            } else {
                player.sendMessage(this.plugin.getPrefix() + this.plugin.getNoPermissions());
            }
        } else {
            sender.sendMessage(this.plugin.getPrefix() + "§cDieser Befehl darf nur von Spielern ausgeführt werden.");
        }

        return true;
    }

}
