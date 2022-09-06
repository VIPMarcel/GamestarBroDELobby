package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record FlyCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(this.plugin.getPrefix() + "§cDieser Befehl darf nur von Spielern ausgeführt werden.");
            return true;
        }
        final Player player = (Player) sender;

        if(player.hasPermission("proxy.staff")) {


            if(arguments.length == 0) {

                if(this.plugin.getFlyMode().contains(player)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    player.sendMessage(this.plugin.getPrefix() + "§eDu darfst nun nicht mehr fliegen.");
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    this.plugin.getFlyMode().remove(player);
                }
                else {

                    if(this.plugin.getJumpAndRunGame().isJumping(player)) {
                        player.sendMessage(this.plugin.getPrefix() + "Du darfst nicht im §eJump'n'Run §7fliegen.");
                        return true;
                    }

                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    player.sendMessage(this.plugin.getPrefix() + "§eDu darfst nun fliegen.");
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    this.plugin.getFlyMode().add(player);
                }

            }
            else if(arguments.length == 1) {

                if(this.plugin.getServer().getPlayer(arguments[0]) == null) {
                    player.sendMessage(this.plugin.getPrefix() + "§7Der angegebene Spieler §e" + arguments[0] + "§7 ist nicht online.");
                    return true;
                }
                final Player target = this.plugin.getServer().getPlayer(arguments[0]);

                if(this.plugin.getFlyMode().contains(target)) {
                    target.playSound(target.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    target.sendMessage(this.plugin.getPrefix() + "§eDu darfst nun nicht mehr fliegen.");
                    player.sendMessage(this.plugin.getPrefix() + target.getDisplayName() + "§e darf nun nicht mehr fliegen.");
                    this.plugin.getFlyMode().remove(target);
                }
                else {

                    if(this.plugin.getJumpAndRunGame().isJumping(target)) {
                        player.sendMessage(this.plugin.getPrefix() + "§e" + target.getName() + " §7versucht sich grade am §eJump'n'Run§7.");
                        target.sendMessage(this.plugin.getPrefix() + "Du darfst nicht im §eJump'n'Run §7fliegen.");
                        return true;
                    }

                    target.playSound(target.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 1F);
                    target.sendMessage(this.plugin.getPrefix() + "§eDu darfst nun fliegen.");
                    player.sendMessage(this.plugin.getPrefix() + target.getDisplayName() + "§e darf nun fliegen.");
                    this.plugin.getFlyMode().add(target);
                }

            }
            else {
                player.sendMessage(this.plugin.getPrefix() + this.plugin.getUnknownCommand());
            }

        }
        else  {
            player.sendMessage(this.plugin.getPrefix() + this.plugin.getNoPermissions());
        }

        return true;
    }

}
