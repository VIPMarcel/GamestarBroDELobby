package vip.marcel.gamestarbro.lobby.utils.locations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import vip.marcel.gamestarbro.lobby.Lobby;
import vip.marcel.gamestarbro.lobby.utils.config.LocationConfiguration;

public class LocationExecutor implements LocationHandler {

    private final Lobby plugin;

    private final LocationConfiguration configuration;

    public LocationExecutor(Lobby plugin) {
        this.plugin = plugin;

        this.configuration = this.plugin.getLocationConfiguration();
    }

    @Override
    public Location getLocation(String name) {
        return new Location(Bukkit.getWorld(
                this.configuration.getString("Locations." + name + ".World")),
                this.configuration.getDouble("Locations." + name + ".X"),
                this.configuration.getDouble("Locations." + name + ".Y"),
                this.configuration.getDouble("Locations." + name + ".Z"),
                this.configuration.getFloat("Locations." + name + ".Yaw"),
                this.configuration.getFloat("Locations." + name + ".Pitch"));
    }

    @Override
    public void saveLocation(String name, Location location) {
        this.configuration.set("Locations." + name + ".World", location.getWorld().getName());
        this.configuration.set("Locations." + name + ".X", location.getX());
        this.configuration.set("Locations." + name + ".Y", location.getY());
        this.configuration.set("Locations." + name + ".Z", location.getZ());
        this.configuration.set("Locations." + name + ".Yaw", location.getYaw());
        this.configuration.set("Locations." + name + ".Pitch", location.getPitch());

        this.configuration.saveConfig();
    }

    @Override
    public void deleteLocation(String name) {
        this.configuration.set("Locations." + name, null);
    }

    @Override
    public boolean doesLocationExists(String name) {
        return this.configuration.getString("Locations." + name + ".World") != null;
    }

}
