package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import vip.marcel.gamestarbro.lobby.Lobby;

public record InventoryClickListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        if(event.getWhoClicked() instanceof Player player) {

            if(event.getCurrentItem() == null) {
                event.setCancelled(true);
                return;
            }

            if(event.getClick().equals(ClickType.NUMBER_KEY)) {
                if (!this.plugin.getEditMode().contains(player)) {
                    event.setCancelled(true);
                }
            }

            if(!this.plugin.getEditMode().contains(player)) {
                event.setCancelled(true);
            }

            if(event.getView().getTitle().equalsIgnoreCase("§8» §aNavigator")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Spawn")) {
                    if(this.plugin.getLocationExecutor().doesLocationExists("Spawn")) {
                        player.teleport(this.plugin.getLocationExecutor().getLocation("Spawn"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    } else {
                        player.sendMessage(this.plugin.getPrefix() + "§cDer Spawn wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                        player.closeInventory();
                    }
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eJump'n'Run")) {
                    if(this.plugin.getLocationExecutor().doesLocationExists("JumpAndRun")) {
                        player.teleport(this.plugin.getLocationExecutor().getLocation("JumpAndRun"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    } else {
                        player.sendMessage(this.plugin.getPrefix() + "§cDie Position wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                        player.closeInventory();
                    }
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dLogin- Streak")) {
                    if(this.plugin.getLocationExecutor().doesLocationExists("LoginStreak")) {
                        player.teleport(this.plugin.getLocationExecutor().getLocation("LoginStreak"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    } else {
                        player.sendMessage(this.plugin.getPrefix() + "§cDie Position wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                        player.closeInventory();
                    }
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTägliche Belohnung")) {
                    if(this.plugin.getLocationExecutor().doesLocationExists("DailyReward")) {
                        player.teleport(this.plugin.getLocationExecutor().getLocation("DailyReward"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    } else {
                        player.sendMessage(this.plugin.getPrefix() + "§cDie Position wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                        player.closeInventory();
                    }
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSurvival")) {
                    if(this.plugin.getLocationExecutor().doesLocationExists("Survival")) {
                        player.teleport(this.plugin.getLocationExecutor().getLocation("Survival"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
                    } else {
                        player.sendMessage(this.plugin.getPrefix() + "§cDie Position wurde noch nicht gesetzt. Bitte kontaktiere einen Administrator.");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                        player.closeInventory();
                    }
                }

            }

        }

    }

}
