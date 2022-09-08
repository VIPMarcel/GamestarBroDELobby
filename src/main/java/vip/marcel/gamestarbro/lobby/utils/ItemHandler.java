package vip.marcel.gamestarbro.lobby.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import vip.marcel.gamestarbro.lobby.Lobby;

public record ItemHandler(Lobby plugin) {

    public void giveItemsToPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(1, this.plugin.item(Material.RECOVERY_COMPASS)
                .setDisplayname("§8» §aNavigator §8┃ §7Unsere Spielmodies")
                .setLore("§8► §7Teleportiere dich zu unseren Spielmodies.")
                .build());

        player.getInventory().setItem(4, this.plugin.item(Material.SHEARS)
                .setDisplayname("§8» §bChallanger §8┃ §7Spieler herausfordern")
                .setLore("§8► §7Fordere andere Spieler heraus.",
                        " ",
                        "§8► §eRechtsklick §7auf einen Spieler",
                        "§8► §eLinksklick §7zum deaktivieren.")
                .build());

        player.getInventory().setItem(7, this.plugin.item(Material.TOTEM_OF_UNDYING)
                .setDisplayname("§8» §cExtras §8┃ §7Gadgets")
                .setLore("§8► §7Benutze coole Extras und zeige anderen Spielern, wer der Boss ist.")
                .build());
    }

}
