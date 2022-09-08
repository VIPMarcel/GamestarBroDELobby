package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import vip.marcel.gamestarbro.lobby.Lobby;

public record PlayerInteractEntityListener(Lobby plugin) implements Listener {

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();

        if(!(this.plugin.getEditMode().contains(player) && player.getGameMode().equals(GameMode.CREATIVE))) {

            if(event.getRightClicked() instanceof Player target) {

                if(player.getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {

                    if(hasInteractCooldown(player)) {
                        return;
                    }

                    final Inventory challanger = Bukkit.createInventory(null, 27, "§8» §bChallanger");

                    setupCooldown(player);

                    for(int i = 0; i < challanger.getSize(); i++) {
                        challanger.setItem(i, this.plugin.item(Material.GRAY_STAINED_GLASS_PANE).setNoName().build());
                    }

                    challanger.setItem(0, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(1, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(9, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

                    challanger.setItem(8, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(7, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(17, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

                    challanger.setItem(18, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(19, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

                    challanger.setItem(26, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
                    challanger.setItem(25, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

                    this.plugin.getChallangerInteracted().put(player, target);

                    if(!this.plugin.getChallanger().containsKey(target)) {

                        if(this.plugin.getChallangerToggled().contains(target)) {
                            player.sendMessage("§8§l┃ §bChallanger §8► §e" + target.getName() + " §cnimmt keine Anfragen an.");
                            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 0.5F, 0.5F);
                            return;
                        }

                        challanger.setItem(10, this.plugin.item(Material.CARVED_PUMPKIN)
                                .setDisplayname("§8» §bHide'n'Seek")
                                .setLore("§8► §7Suche §e" + target.getName() + " §7in der Lobby.")
                                .build());

                    } else {
                        if(this.plugin.getChallanger().get(target).equals(player)) {

                            challanger.setItem(11, this.plugin.item(Material.GREEN_TERRACOTTA)
                                    .setDisplayname("§8» §aAnnehmen")
                                    .build());

                            challanger.setItem(13, this.plugin.item(Material.SPRUCE_SIGN)
                                    .setDisplayname("§8» §bHide'n'Seek")
                                    .setLore("§8► §7Verstecke dich vor §e" + target.getName() + " §7in der Lobby.")
                                    .build());

                            challanger.setItem(15, this.plugin.item(Material.RED_TERRACOTTA)
                                    .setDisplayname("§8» §cAblehnen")
                                    .build());

                        } else {

                            if(this.plugin.getChallangerToggled().contains(target)) {
                                player.sendMessage("§8§l┃ §bChallanger §8► §e" + target.getName() + " §cnimmt keine Anfragen an.");
                                player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 0.5F, 0.5F);
                                return;
                            }

                            challanger.setItem(10, this.plugin.item(Material.CARVED_PUMPKIN)
                                    .setDisplayname("§8» §bHide'n'Seek")
                                    .setLore("§8► §7Suche §e" + target.getName() + " §7in der Lobby.")
                                    .build());

                        }
                    }

                    player.openInventory(challanger);
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5F, 0.5F);

                }

            } else {
                event.setCancelled(true);
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
