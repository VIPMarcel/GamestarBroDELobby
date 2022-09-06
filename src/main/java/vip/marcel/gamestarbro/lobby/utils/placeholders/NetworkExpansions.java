package vip.marcel.gamestarbro.lobby.utils.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.marcel.gamestarbro.lobby.Lobby;

public class NetworkExpansions extends PlaceholderExpansion {

    private final Lobby plugin;

    public NetworkExpansions(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "network";
    }

    @Override
    public @NotNull String getAuthor() {
        return "VIPMarcel";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player == null) {
            return "";
        }

        if(params.equals("coins")) {
            return String.valueOf(this.plugin.getDatabasePlayers().getCoins(player.getUniqueId()));
        }

        if(params.equals("playtime")) {
            return this.plugin.getSimpleTimeString(this.plugin.getDatabasePlayers().getPlayTime(player.getUniqueId()));
        }

        return null;
    }

}
