package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerInteractListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if(!(this.plugin.getEditMode().contains(player) && player.getGameMode().equals(GameMode.CREATIVE))) {
            event.setCancelled(true);
        }

        if(player.getInventory().getItemInMainHand().getItemMeta() == null) {
            return;
        }

        if(this.plugin.getEditMode().contains(player)) {

            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§8» §7JnR §8| §aBlöcke auswählen")) {

                    if(this.plugin.getSetupJnRCooldown().contains(player)) {
                        return;
                    }

                    if(!this.plugin.getSetupJnR().containsKey(player)) {
                        this.plugin.getSetupJnR().put(player, 1);
                    }

                    final Integer blockNumber = this.plugin.getSetupJnR().get(player);

                    this.plugin.getLocationExecutor().saveLocation("JumpNRun." + blockNumber, event.getClickedBlock().getLocation());

                    player.sendMessage("§8§l┃ §6Jump'n'Run §8► §7" + "§7Block §e" + blockNumber + " §7wurde gespeichert.");
                    player.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_CANDLE_BREAK, 0.5F, 0.5F);

                    this.plugin.getSetupJnR().put(player, blockNumber + 1);

                    this.plugin.getSetupJnRCooldown().add(player);

                    Bukkit.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                        this.plugin.getSetupJnRCooldown().remove(player);
                    }, 20);

                }
            }

        } else {
            // No Edit-Mode:

            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {

                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§8» §aNavigator §8┃ §7Unsere Spielmodies")) {
                    event.setCancelled(true);

                    if(!hasInteractCooldown(player)) {
                        setupCooldown(player);
                        player.openInventory(this.plugin.getInventoryHandler().getNavigator());
                        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5F, 0.5F);
                    }

                }

                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§8» §cExtras §8┃ §7Gadgets")) {
                    event.setCancelled(true);

                    if(!hasInteractCooldown(player)) {
                        setupCooldown(player);
                        player.sendMessage(this.plugin.getGlobalPrefix() + "§7Für diese Funktion wird das Konzept noch entwickelt.");
                        player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 0.5F, 0.5F);
                    }
                }

            }

            if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {

                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§8» §bChallanger §8┃ §7Spieler herausfordern")) {
                    event.setCancelled(true);

                    if(this.plugin.getChallangerToggled().contains(player)) {
                        this.plugin.getChallangerToggled().remove(player);
                        player.sendMessage("§8§l┃ §bChallanger §8► §7" + "§aDu empfängst nun wieder Herausforderungen.");
                        player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 0.25F, 0.25F);
                    } else {
                        this.plugin.getChallangerToggled().add(player);
                        player.sendMessage("§8§l┃ §bChallanger §8► §7" + "§cDu empfängst nun keine Herausforderungen mehr.");
                        player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 0.5F, 0.5F);
                    }

                }

            }

        }

    }

    private boolean hasInteractCooldown(Player player) {
        return this.plugin.getInteractCooldown().contains(player);
    }

    private void setupCooldown(Player player) {
        this.plugin.getInteractCooldown().add(player);

        Bukkit.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            this.plugin.getInteractCooldown().remove(player);
        }, 10);
    }

}
