package vip.marcel.gamestarbro.lobby.utils.locations;

import org.bukkit.Location;

public interface LocationHandler {

    Location getLocation(String name);

    void saveLocation(String name, Location location);

    void deleteLocation(String name);

    boolean doesLocationExists(String name);

}
