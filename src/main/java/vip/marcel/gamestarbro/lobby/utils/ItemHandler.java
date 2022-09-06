package vip.marcel.gamestarbro.lobby.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import vip.marcel.gamestarbro.lobby.Lobby;

public record ItemHandler(Lobby plugin) {

    public void giveItemsToPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(2, this.plugin.item(Material.RECOVERY_COMPASS)
                .setDisplayname("§8» §aNavigator")
                .setLore("§8► §7Teleportiere dich zu unseren Spielmodies.")
                .build());

        player.getInventory().setItem(6, this.plugin.item(Material.TOTEM_OF_UNDYING)
                .setDisplayname("§8» §cExtras")
                .setLore("§8► §7Benutze coole Extras und zeige anderen Spielern, wer der Boss ist.")
                .build());
    }

}
