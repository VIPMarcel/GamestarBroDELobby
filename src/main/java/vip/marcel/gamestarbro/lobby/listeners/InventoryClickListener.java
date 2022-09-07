package vip.marcel.gamestarbro.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
                    final Inventory loginStreak = this.plugin.getInventoryHandler().getLoginStreak();
                    final AtomicInteger playerStreak = new AtomicInteger(0);
                    final AtomicBoolean alreadyCollected = new AtomicBoolean(true);

                    CompletableFuture.runAsync(() -> {
                        playerStreak.set(this.plugin.getDatabasePlayers().getLoginStreak(player.getUniqueId()));
                        alreadyCollected.set(this.plugin.getDatabasePlayers().getLoginStreakCollected(player.getUniqueId()));
                    }).thenAccept(unused -> {
                        Bukkit.getScheduler().runTask(this.plugin, () -> {

                            if(playerStreak.get() == 3) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(10, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e3 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(10, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e3 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(10, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e3 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() == 5) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(11, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e5 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(11, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e5 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(11, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e5 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() == 7) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(12, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e7 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(12, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e7 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(12, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e7 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() == 10) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(13, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e10 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(13, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e10 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(13, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e10 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() == 14) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(14, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e14 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(14, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e14 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(14, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e14 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() == 21) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(15, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e21 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(15, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e21 §7Tage")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(15, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e21 §7Tage")
                                        .build());

                            }

                            if(playerStreak.get() >= 30) {

                                if(alreadyCollected.get()) {
                                    loginStreak.setItem(16, this.plugin.item(Material.GRAY_DYE)
                                            .setDisplayname("§e30 §7Tage, aufwärts")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §eDu hast diese Belohnung bereits abgeholt")
                                            .build());
                                } else {
                                    loginStreak.setItem(16, this.plugin.item(Material.LIME_DYE)
                                            .setDisplayname("§e30 §7Tage, aufwärts")
                                            .addEnchantment(Enchantment.DURABILITY, 1)
                                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                            .setLore("§8► §aDu kannst diese Belohnung abholen.")
                                            .build());
                                }

                            } else {

                                loginStreak.setItem(16, this.plugin.item(Material.GRAY_DYE)
                                        .setDisplayname("§e30 §7Tage, aufwärts")
                                        .build());

                            }

                            player.openInventory(loginStreak);
                            player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_AMBIENT, 0.5F, 0.5F);

                        });
                    });
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTägliche Belohnung")) {
                    final Inventory dailyReward = this.plugin.getInventoryHandler().getDailyReward();
                    final AtomicLong timeStamp = new AtomicLong();

                    CompletableFuture.runAsync(() -> {
                        timeStamp.set(this.plugin.getDatabaseDailyReward().getTimeStamp(player.getUniqueId()));
                    }).thenAccept(unused -> {
                       Bukkit.getScheduler().runTask(this.plugin, () -> {

                           if(timeStamp.get() == -1 | timeStamp.get() <= System.currentTimeMillis()) {

                               dailyReward.setItem(13, this.plugin.item(Material.CHEST_MINECART)
                                       .setDisplayname("§eTägliche Belohnung")
                                       .addEnchantment(Enchantment.DURABILITY, 1)
                                       .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                       .setLore("§8► §aDu kannst deine Belohnung abholen.")
                                       .build());

                           } else {

                               Calendar calendar = Calendar.getInstance();
                               calendar.setTimeInMillis(timeStamp.get());
                               Date date = calendar.getTime();
                               SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                               final String dateString = sdf.format(date);

                               final String hours = dateString.split(":")[0];
                               final String minutes = dateString.split(":")[1];

                               dailyReward.setItem(13, this.plugin.item(Material.MINECART)
                                       .setDisplayname("§eTägliche Belohnung")
                                       .addEnchantment(Enchantment.DURABILITY, 1)
                                       .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                       .setLore("§8► §cNächste Belohnung um §8» §e" + hours + "§c:§e" + minutes + " Uhr§c.")
                                       .build());

                           }

                           player.openInventory(dailyReward);
                           player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_ENTER, 0.5F, 0.5F);

                       });
                    });
                }

                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSurvival")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10F, 10F);
                    this.plugin.connectToServer(player, "Survival");
                }

            }

            if(event.getView().getTitle().equalsIgnoreCase("§8» §dLogin- Streak")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType().equals(Material.LIME_DYE)) {
                    final AtomicInteger playerStreakCount = new AtomicInteger(0);

                    CompletableFuture.runAsync(() -> {
                        playerStreakCount.set(this.plugin.getDatabasePlayers().getLoginStreak(player.getUniqueId()));
                        this.plugin.getDatabasePlayers().setCoins(player.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(player.getUniqueId()) + (200 * playerStreakCount.get()));
                        this.plugin.getDatabasePlayers().setLoginStreakCollected(player.getUniqueId(), true);
                    }).thenAccept(unused -> {
                        Bukkit.getScheduler().runTask(this.plugin, () -> {

                            final int rewardCoins = (200 * playerStreakCount.get());

                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, 10F);
                            player.sendTitle("§d§oBelohnung", "§a+ §e" + rewardCoins + " Coins", 15, 20, 15);
                            player.closeInventory();

                        });
                    });

                }

                if(event.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                    player.closeInventory();
                }

            }

            if(event.getView().getTitle().equalsIgnoreCase("§8» §bTägliche Belohnung")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType().equals(Material.CHEST_MINECART)) {
                    final long nextRewardTimeStamp = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
                    final int rewardCoins = ThreadLocalRandom.current().nextInt(100, 250);

                    CompletableFuture.runAsync(() -> {
                        this.plugin.getDatabaseDailyReward().setTimeStamp(player.getUniqueId(), nextRewardTimeStamp);
                        this.plugin.getDatabasePlayers().setCoins(player.getUniqueId(), this.plugin.getDatabasePlayers().getCoins(player.getUniqueId()) + rewardCoins);
                    }).thenAccept(unused -> {
                        Bukkit.getScheduler().runTask(this.plugin, () -> {

                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10F, 10F);
                            player.sendTitle("§b§oBelohnung", "§a+ §e" + rewardCoins + " Coins", 15, 20, 15);
                            player.closeInventory();

                        });
                    });

                }

                if(event.getCurrentItem().getType().equals(Material.MINECART)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 0.5F);
                    player.closeInventory();
                }

            }

        }

    }

}
