package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record EditCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.getPrefix() + "§cDieser Befehl darf nur von Spielern ausgeführt werden.");
            return true;
        }
        final Player player = (Player) sender;

        if(player.hasPermission("proxy.admin")) {

            if(arguments.length == 0) {

                if(this.plugin.getEditMode().contains(player)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    player.sendMessage(this.plugin.getPrefix() + "§eDu darfst die Lobby nun nicht mehr verändern.");
                    player.setGameMode(GameMode.ADVENTURE);
                    player.spigot().setCollidesWithEntities(false);
                    this.plugin.getEditMode().remove(player);

                    this.plugin.getItemHandler().giveItemsToPlayer(player);
                }
                else {
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    player.sendMessage(this.plugin.getPrefix() + "§eDu darfst die Lobby absofort verändern.");
                    player.setGameMode(GameMode.CREATIVE);
                    player.getInventory().clear();
                    player.spigot().setCollidesWithEntities(true);
                    this.plugin.getEditMode().add(player);
                }

            }
            else if(arguments.length == 1) {

                if(this.plugin.getServer().getPlayer(arguments[0]) == null) {
                    player.sendMessage(this.plugin.getPrefix() + "§7Der angegebene Spieler §e" + arguments[0] + "§7 ist nicht online.");
                    return true;
                }
                final Player target = this.plugin.getServer().getPlayer(arguments[0]);

                if(this.plugin.getEditMode().contains(target)) {
                    target.playSound(target.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    target.sendMessage(this.plugin.getPrefix() + "§eDu darfst die Lobby nun nicht mehr verändern.");
                    player.sendMessage(this.plugin.getPrefix() + target.getDisplayName() + "§e darf die Lobby nun nicht mehr verändern.");
                    target.setGameMode(GameMode.ADVENTURE);
                    target.spigot().setCollidesWithEntities(false);
                    this.plugin.getEditMode().remove(target);

                    this.plugin.getItemHandler().giveItemsToPlayer(target);
                }
                else {
                    target.playSound(target.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    target.sendMessage(this.plugin.getPrefix() + "§eDu darfst die Lobby absofort verändern.");
                    player.sendMessage(this.plugin.getPrefix() + target.getDisplayName() + "§e darf die Lobby absofort verändern.");
                    target.setGameMode(GameMode.CREATIVE);
                    target.getInventory().clear();
                    target.spigot().setCollidesWithEntities(true);
                    this.plugin.getEditMode().add(target);
                }

            }
            else {
                player.sendMessage(this.plugin.getPrefix() + this.plugin.getUnknownCommand());
            }

        }
        else {
            player.sendMessage(this.plugin.getPrefix() + this.plugin.getNoPermissions());
        }


        return true;
    }

}
