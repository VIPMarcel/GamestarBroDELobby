package vip.marcel.gamestarbro.lobby.commands;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vip.marcel.gamestarbro.lobby.Lobby;

public record CrashCommand(Lobby plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if(sender.hasPermission("proxy.admin")) {

            if(arguments.length == 0) {
                sender.sendMessage(this.plugin.getPrefix() + "§7Du musst einen §eSpielernamen §7angeben, um ihn zu crashen.");
                return true;
            }

            else if(arguments.length == 1) {
                final Player target = this.plugin.getServer().getPlayer(arguments[0]);

                if(target == null) {
                    sender.sendMessage(this.plugin.getPrefix() + "§7Der Spieler §e" + arguments[0] + "§7 wurde nicht gefunden.");
                    return true;
                }

                if(sender instanceof Player) {
                    final Player player = (Player) sender;
                    player.teleport(target);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);
                }

                target.spawnParticle(Particle.EXPLOSION_HUGE, target.getLocation(), Integer.MAX_VALUE);

                sender.sendMessage(this.plugin.getPrefix() + "§7Du hast den §eMinecraft-Client §7von §e" + target.getName() + " §7gecrasht.");

            }

            else {
                sender.sendMessage(this.plugin.getPrefix() + this.plugin.getUnknownCommand());
            }



        }
        else {
            sender.sendMessage(this.plugin.getNoPermissions());
        }

        return true;
    }

}
