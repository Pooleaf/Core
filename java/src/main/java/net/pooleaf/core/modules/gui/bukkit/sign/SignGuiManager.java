package net.pooleaf.core.modules.gui.bukkit.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.pooleaf.core.BukkitCoreBootstrapPlugin;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.support.common.manager.AbstractSyncManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SignGuiManager extends AbstractSyncManager<UUID, SignGui> {

    public List<Player> getViewers(SignGui signGui) {
        return getDatas().entrySet().stream()
                .filter(entry -> entry.getValue().equals(signGui))
                .map(entry -> Bukkit.getPlayer(entry.getKey()))
                .collect(Collectors.toList());
    }

    public void registerPacketListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BukkitCoreBootstrapPlugin.getInstance(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                SignGui signGui = GuiModule.getSignGuiManager().get(player.getUniqueId());
                if (signGui == null) {
                    return;
                }

                String[] lines = new String[4];
                int i = 0;
                for (WrappedChatComponent comp : event.getPacket().getChatComponentArrays().read(0)) {
                    lines[i] = comp.getJson().substring(1, comp.getJson().length() - 1);
                    i++;
                }

                event.setCancelled(true);
                signGui.onSignComplete(event.getPlayer(), lines);

                event.getPlayer().sendBlockChange(new Location(event.getPlayer().getWorld(), 0, 0, 0), Material.AIR, (byte) 0);
            }
        });
    }

}