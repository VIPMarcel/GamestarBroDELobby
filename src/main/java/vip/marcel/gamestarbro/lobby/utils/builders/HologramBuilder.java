package vip.marcel.gamestarbro.lobby.utils.builders;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HologramBuilder {

    private final Lobby plugin;

    private final ProtocolManager manager;

    private final List<PacketContainer> armorStands;
    private final List<PacketContainer> metaDatas;
    private final List<Integer> entityIds;

    private final Location location;

    private final double distance;

    private int count;

    public HologramBuilder(Lobby plugin, Location location, String... lines) {
        this.plugin = plugin;

        this.manager = ProtocolLibrary.getProtocolManager();

        this.armorStands = Lists.newArrayList();
        this.metaDatas = Lists.newArrayList();
        this.entityIds = Lists.newArrayList();

        this.location = location;
        this.distance = 0.235D;
        this.count = 0;

        final List<String> hologramLines = Lists.newArrayList();
        hologramLines.addAll(Arrays.asList(lines));

        hologramLines.forEach(line -> {
            int entityId = (int) (Math.random() * Integer.MAX_VALUE);

            PacketContainer armorStand = this.manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

            armorStand.getModifier()
                    .writeDefaults();
            armorStand.getEntityTypeModifier()
                    .write(0, EntityType.ARMOR_STAND);

            armorStand.getIntegers()
                    .write(0, entityId)
                    .write(1, 1);

            armorStand.getUUIDs()
                    .write(0, UUID.randomUUID());

            armorStand.getDoubles()
                    .write(0, this.location.getX())
                    .write(1, this.location.getY())
                    .write(2, this.location.getZ());


            PacketContainer metaData = this.manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

            metaData.getModifier()
                    .writeDefaults();

            metaData.getIntegers()
                    .write(0, entityId);


            WrappedDataWatcher data = new WrappedDataWatcher();
            data.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) (0x20)); // invisible

            if(line.equalsIgnoreCase(" ")) {
                data.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), false); // custom name visibile
            }
            else {
                Optional<?> opt = Optional
                        .of(WrappedChatComponent
                                .fromText(line).getHandle());
                data.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt); // custom name

                data.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); // custom name visibile
            }

            metaData.getWatchableCollectionModifier()
                    .write(0, data.getWatchableObjects());


            this.armorStands.add(armorStand);
            this.metaDatas.add(metaData);
            this.entityIds.add(entityId);

            this.location.subtract(0.0D, this.distance, 0.0D);
            this.count++;
        });

        for(int i = 0; i < this.count; i++) {
            this.location.add(0.0D, this.distance, 0.0D);
        }

        this.count = 0;
    }

    public void showHologramForAll() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            this.showHologramForPlayer(players);
        });
    }

    public void showHologramForAllTemporary(long time) {
        Bukkit.getOnlinePlayers().forEach(players -> {
            this.showHologramForPlayerTemporary(players, time);
        });
    }

    private void hideHologramForAll() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            this.hideHologramForPlayer(players);
        });
    }

    public void showHologramForPlayer(Player player) {
        this.armorStands.forEach(packet -> {
            this.manager.sendServerPacket(player, packet);
        });

        this.metaDatas.forEach(packet -> {
            this.manager.sendServerPacket(player, packet);
        });
    }

    public void showHologramForPlayerTemporary(Player player, long time) {
        this.showHologramForPlayer(player);
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> this.hideHologramForPlayer(player), time);
    }

    private void hideHologramForPlayer(Player player) {
        PacketContainer packet = this.manager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        //packet.getIntegers().write(0, this.entityIds.size());
        packet.getIntLists().write(0, this.entityIds);

        this.manager.sendServerPacket(player, packet);
    }

}