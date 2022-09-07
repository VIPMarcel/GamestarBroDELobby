package vip.marcel.gamestarbro.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import vip.marcel.gamestarbro.lobby.Lobby;

public class InventoryHandler {

    private Lobby plugin;

    private Inventory navigator, loginStreak, dailyReward;

    public InventoryHandler(Lobby plugin) {
        this.plugin = plugin;

        this.navigator = Bukkit.createInventory(null, 54, "§8» §aNavigator");
        this.loginStreak = Bukkit.createInventory(null, 27, "§8» §dLogin- Streak");
        this.dailyReward = Bukkit.createInventory(null, 27, "§8» §bTägliche Belohnung");

        this.fillNavigator();
        this.fillLoginStreak();
        this.fillDailyReward();
    }

    private void fillNavigator() {

        for(int i = 0; i < this.navigator.getSize(); i++) {
            this.navigator.setItem(i, this.plugin.item(Material.GRAY_STAINED_GLASS_PANE).setNoName().build());
        }

        this.navigator.setItem(0, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(1, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(9, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.navigator.setItem(7, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(8, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(17, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.navigator.setItem(44, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(52, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(53, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.navigator.setItem(36, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(45, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.navigator.setItem(46, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());


        this.navigator.setItem(12, this.plugin.item(Material.GRASS_BLOCK)
                .setDisplayname("§aSurvival")
                .setLore("§8► §7Du kannst alleine oder mit deinen Freunden",
                        "   §7ein Stück Land beanspruchen und dort ungestört bauen.",
                        "§8► §aViele Spieler haben Shops, schau dich doch mal um!")
                .build());

        this.navigator.setItem(14, this.plugin.item(Material.GOLDEN_BOOTS)
                .setDisplayname("§eJump'n'Run")
                .setLore("§8► §7Viel Glück, am Ende erhältst du §e500 Coins§7.")
                .build());

        this.navigator.setItem(29, this.plugin.item(Material.EXPERIENCE_BOTTLE)
                .setDisplayname("§dLogin- Streak")
                .setLore("§8► §7Hier kannst du deine §aBelohnungen §7abholen,",
                        "   §7sobald du §cStreaks §7abgeschlossen hast.")
                .build());

        this.navigator.setItem(33, this.plugin.item(Material.FIREWORK_ROCKET)
                .setDisplayname("§bTägliche Belohnung")
                .setLore("§8► §7Schaue §ejeden Tag §7vorbei und erhalte tolle §aBelohnungen§7.")
                .build());

        this.navigator.setItem(40, this.plugin.item(Material.HEART_OF_THE_SEA)
                .setDisplayname("§7Spawn")
                .setLore("§8► §7Teleportiere dich zu dem Lobby-Spawnpunkt.")
                .build());
    }

    private void fillLoginStreak() {

        for(int i = 0; i < this.loginStreak.getSize(); i++) {
            this.loginStreak.setItem(i, this.plugin.item(Material.GRAY_STAINED_GLASS_PANE).setNoName().build());
        }

        this.loginStreak.setItem(0, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(1, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(9, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.loginStreak.setItem(8, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(7, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(17, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.loginStreak.setItem(18, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(19, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.loginStreak.setItem(26, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.loginStreak.setItem(25, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

    }

    private void fillDailyReward() {

        for(int i = 0; i < this.dailyReward.getSize(); i++) {
            this.dailyReward.setItem(i, this.plugin.item(Material.GRAY_STAINED_GLASS_PANE).setNoName().build());
        }

        this.dailyReward.setItem(0, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(1, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(9, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.dailyReward.setItem(8, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(7, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(17, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.dailyReward.setItem(18, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(19, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

        this.dailyReward.setItem(26, this.plugin.item(Material.ORANGE_STAINED_GLASS_PANE).setNoName().build());
        this.dailyReward.setItem(25, this.plugin.item(Material.YELLOW_STAINED_GLASS_PANE).setNoName().build());

    }

    public Inventory getNavigator() {
        return this.navigator;
    }

    public Inventory getLoginStreak() {
        return this.loginStreak;
    }

    public Inventory getDailyReward() {
        return this.dailyReward;
    }

}
